package me.zhengjie.modules.quartz.task;

import me.zhengjie.modules.stock.service.StockMonitorService;
import me.zhengjie.modules.stock.util.ConstantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author masterJ
 * @create 2020-05-15 22:33
 * 股票监控，根据类型来监控，方便扩展
 */
@Component
public class StockMonitorTask {
    @Autowired
    private StockMonitorService stockMonitorService;

    private final LocalTime MORNING_START = LocalTime.of(9, 30, 0);
    private final LocalTime MORNING_END = LocalTime.of(11, 30, 0);
    private final LocalTime AFTERNOON_START = LocalTime.of(13, 0, 0);
    private final LocalTime AFTERNOON_END = LocalTime.of(15, 0, 0);

    /**
     * 监控股票
     */
    public void runStock(){
        if (checkTradingHour()){
            stockMonitorService.monitor(ConstantUtil.TYPE_STOCK);
        }
    }

    /**
     * 监控可以转换债券
     */
    public void runBond(){
        if (checkTradingHour()){
            stockMonitorService.monitor(ConstantUtil.TYPE_BOND);
        }
    }

    /**
     * 监控etf
     */
    public void runEtf(){
        if (checkTradingHour()){
            stockMonitorService.monitor(ConstantUtil.TYPE_ETF);
        }
    }

    /**
     * 监控指数
     */
    public void runIndex(){
        if (checkTradingHour()){
            stockMonitorService.monitor(ConstantUtil.TYPE_INDEX);
        }
    }

    /**
     * 检测是否是交易时间
     * 只运行9:30 - 11:30  13:00 - 15:00
     * @return
     */
    private boolean checkTradingHour(){
        return (LocalTime.now().isAfter(MORNING_START) && LocalTime.now().isBefore(MORNING_END)) || (LocalTime.now().isAfter(AFTERNOON_START) && LocalTime.now().isBefore(AFTERNOON_END));
    }
}
