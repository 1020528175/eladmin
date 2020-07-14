package me.zhengjie.modules.stock.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.zhengjie.domain.EmailConfig;
import me.zhengjie.domain.vo.EmailVo;
import me.zhengjie.modules.stock.domain.StockMonitor;
import me.zhengjie.modules.stock.repository.StockMonitorRepository;
import me.zhengjie.modules.stock.service.StockMonitorService;
import me.zhengjie.modules.stock.service.dto.StockMonitorDTO;
import me.zhengjie.modules.stock.service.dto.StockMonitorQueryCriteria;
import me.zhengjie.modules.stock.service.mapper.StockMonitorMapper;
import me.zhengjie.modules.system.service.UserService;
import me.zhengjie.service.EmailService;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import me.zhengjie.utils.SecurityUtils;
import me.zhengjie.utils.ValidationUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
* @author masterJ
* @date 2020-05-15
*/
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class StockMonitorServiceImpl implements StockMonitorService {

    @Value("${custom.stock.current_time_url}")
    private String stockCurrentTimeUrl;

    /**
     * 股市操盘手
     */
    @Value(("${send.stock}"))
    private String stock;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EmailService emailService;

    @Autowired
    private StockMonitorRepository stockMonitorRepository;

    @Autowired
    private StockMonitorMapper stockMonitorMapper;

    @Autowired
    private UserService userService;

    @Override
    public Object queryAll(StockMonitorQueryCriteria criteria, Pageable pageable){
        Page<StockMonitor> page = stockMonitorRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(stockMonitorMapper::toDto));
    }

    @Override
    public Object queryAll(StockMonitorQueryCriteria criteria){
        return stockMonitorMapper.toDto(stockMonitorRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public StockMonitorDTO findById(Long id) {
        Optional<StockMonitor> stockMonitor = stockMonitorRepository.findById(id);
        ValidationUtil.isNull(stockMonitor,"StockMonitor","id",id);
        return stockMonitorMapper.toDto(stockMonitor.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StockMonitorDTO create(StockMonitor resources) {
        resources.setCreateBy(SecurityUtils.getUsername());
        resources.setCreateDate(new Timestamp(System.currentTimeMillis()));
        return stockMonitorMapper.toDto(stockMonitorRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(StockMonitor resources) {
        Optional<StockMonitor> optionalStockMonitor = stockMonitorRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalStockMonitor,"StockMonitor","id",resources.getId());
        StockMonitor stockMonitor = optionalStockMonitor.get();
        stockMonitor.copy(resources);
        stockMonitor.setUpdateBy(SecurityUtils.getUsername());
        stockMonitor.setUpdateDate(new Timestamp(System.currentTimeMillis()));
        stockMonitorRepository.save(stockMonitor);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        stockMonitorRepository.deleteById(id);
    }

    @Override
    public void monitor(String type) {
        List<StockMonitor> stockMonitors = stockMonitorRepository.findByType(type);
        EmailConfig emailConfig = emailService.find();
        //封装发送邮件对象
        EmailVo emailVo = EmailVo.builder().ccs(Arrays.asList(emailConfig.getFromUser())).tos(userService.getEmailByJob(stock)).build();

        for (StockMonitor stockMonitor : stockMonitors) {
            String result = restTemplate.getForObject(stockCurrentTimeUrl + stockMonitor.getCode(), String.class);
            result = StringUtils.substringBetween(result,"\"", "\"");
            String[] results = StringUtils.split(result, ",");
            // results数据依次是“股票名称、今日开盘价、昨日收盘价、当前价格、今日最高价、今日最低价、竞买价、竞卖价、成交股数、成交金额、买1手、买1报价、买2手、买2报价、…、买5报价、…、卖5报价、日期、时间”。
            if (stockMonitor.getMaxPrice() != null && new BigDecimal(results[3]).compareTo(stockMonitor.getMaxPrice()) >= 0){
                log.warn("价格已大于监控最高价: " + Arrays.toString(results));
                emailVo.setSubject(String.format("重要：%s，价格已大于监控最高价%s，请坚持自我", stockMonitor.getName(), stockMonitor.getMaxPrice().toString()));
                emailVo.setContent(String.format("重要：%s，价格已大于监控最高价%s，请你马上查看！！！，请坚持自我", stockMonitor.getName(), stockMonitor.getMaxPrice().toString()));
                try {
                    emailService.send(emailVo,emailConfig);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if (stockMonitor.getMinPrice() != null && new BigDecimal(results[3]).compareTo(stockMonitor.getMinPrice()) <= 0){
                log.warn("价格已低于监控最低价: " + Arrays.toString(results));
                log.warn("价格已低于监控最低价: " + stockMonitor.getMinPrice());
                emailVo.setSubject(String.format("重要：%s，价格已低于监控最低价%s，请坚持自我", stockMonitor.getName(), stockMonitor.getMinPrice().toString()));
                emailVo.setContent(String.format("重要：%s，价格已低于监控最低价%s，请你马上查看！！！，请坚持自我", stockMonitor.getName(), stockMonitor.getMinPrice().toString()));
                try {
                    emailService.send(emailVo,emailConfig);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if (stockMonitor.getFallRate() != null){
                // ((实时价 - 前一日收盘价)/ 前一日收盘价) * 100;
                BigDecimal fallRate = (new BigDecimal(results[3]).subtract(new BigDecimal(results[2]))).divide(new BigDecimal(results[2]), 5, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
                if (fallRate.signum() < 0 && fallRate.abs().compareTo(stockMonitor.getFallRate()) >= 0){
                    log.warn("跌幅已超过监控跌幅: " + Arrays.toString(results));
                    // fallRate是跌幅，且大于设定的跌幅
                    emailVo.setSubject(String.format("重要：%s，跌幅已超过监控跌幅%s，请坚持自我", stockMonitor.getName(), stockMonitor.getFallRate().setScale(2).toString() + "%"));
                    emailVo.setContent(String.format("重要：%s，跌幅已超过监控跌幅%s，请你马上查看！！！，请坚持自我", stockMonitor.getName(), stockMonitor.getFallRate().setScale(2).toString() + "%"));
                    try {
                        emailService.send(emailVo,emailConfig);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }


    @Override
    public List<StockMonitor> findByType(String type) {
        return stockMonitorRepository.findByType(type);
    }

}