package me.zhengjie.modules.mall.service.impl;

import me.zhengjie.modules.mall.domain.GoodsMonitor;
import me.zhengjie.modules.mall.repository.GoodsMonitorRepository;
import me.zhengjie.modules.mall.service.GoodsMonitorService;
import me.zhengjie.modules.mall.service.dto.GoodsMonitorDTO;
import me.zhengjie.modules.mall.service.dto.GoodsMonitorQueryCriteria;
import me.zhengjie.modules.mall.service.mapper.GoodsMonitorMapper;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.PatternUtil;
import me.zhengjie.utils.QueryHelp;
import me.zhengjie.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;

/**
* @author masterJ
* @date 2019-09-06
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class GoodsMonitorServiceImpl implements GoodsMonitorService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private GoodsMonitorRepository goodsMonitorRepository;

    @Autowired
    private GoodsMonitorMapper goodsMonitorMapper;

    @Override
    public Object queryAll(GoodsMonitorQueryCriteria criteria, Pageable pageable){
        Page<GoodsMonitor> page = goodsMonitorRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(goodsMonitorMapper::toDto));
    }

    @Override
    public Object queryAll(GoodsMonitorQueryCriteria criteria){
        return goodsMonitorMapper.toDto(goodsMonitorRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public GoodsMonitorDTO findById(Long id) {
        Optional<GoodsMonitor> goodsMonitor = goodsMonitorRepository.findById(id);
        ValidationUtil.isNull(goodsMonitor,"GoodsMonitor","id",id);
        return goodsMonitorMapper.toDto(goodsMonitor.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GoodsMonitorDTO create(GoodsMonitor resources) {
        resources.setCreateBy("admin");
        resources.setCreateDate(new Timestamp(System.currentTimeMillis()));
        return goodsMonitorMapper.toDto(goodsMonitorRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(GoodsMonitor resources) {
        Optional<GoodsMonitor> optionalGoodsMonitor = goodsMonitorRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalGoodsMonitor,"GoodsMonitor","id",resources.getId());
        GoodsMonitor goodsMonitor = optionalGoodsMonitor.get();
        goodsMonitor.copy(resources);
        goodsMonitorRepository.save(goodsMonitor);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        goodsMonitorRepository.deleteById(id);
    }

    @Override
    public GoodsMonitor getGoodsInfo(GoodsMonitor goodsMonitor) {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(goodsMonitor.getLink(), String.class);
        String body = responseEntity.getBody();

        //<p class="pib-title-detail" title="周大福 车花镂空足金镯子儿童黄金手镯">周大福 车花镂空足金镯子儿童黄金手镯</p>
        Matcher titleMatcher = PatternUtil.VIP_PATTERN_TITLE.matcher(body);
        if (titleMatcher.find()){
            System.out.println("matcher1.group(2) = " + titleMatcher.group(2));
            goodsMonitor.setTitle(titleMatcher.group(2));
        }

        //<a href="//a.vpimg3.com/upload/merchandise/pdcvis/2019/07/15/7/e3e8d3eb-9124-4d8c-a2c0-89fa8dcf6bbe.jpg" class="J-mer-bigImgZoom" rel="undefined" style="outline-style: none; text-decoration: none;" title="">
        Matcher imageMatcher = PatternUtil.VIP_PATTERN_IMAGE.matcher(body);
        if (imageMatcher.find()){
            System.out.println("matcher1.group(2) = " + imageMatcher.group(2));
            goodsMonitor.setImgUrl(imageMatcher.group(2));
        }
        goodsMonitor.setOriginMall("唯品会");
        return goodsMonitor;
    }

    @Override
    public void monitorGoodsPrice() {
        List<GoodsMonitor> goodsMonitors = goodsMonitorRepository.findAll();
        goodsMonitors.forEach(goodsMonitor -> {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(goodsMonitor.getLink(), String.class);
            String body = responseEntity.getBody();
            //通过正则来获取 商品的标题，图片地址，价格
            Matcher priceMatcher = PatternUtil.VIP_PATTERN_PRICE.matcher(body);
            if (priceMatcher.find()){
                BigDecimal price = new BigDecimal(priceMatcher.group(2));
                if (price.compareTo(goodsMonitor.getMinPrice()) == -1){
                    //发送降价邮件
                }else if(goodsMonitor.getMaxPrice() != null && price.compareTo(goodsMonitor.getMaxPrice()) == 1){
                    //发送涨价邮件
                }
            }
        });
    }


}