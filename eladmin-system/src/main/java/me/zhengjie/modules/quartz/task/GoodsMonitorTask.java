package me.zhengjie.modules.quartz.task;

import me.zhengjie.modules.mall.service.GoodsMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author master
 * @create 2019-09-06 17:24
 * 商品监控的定时任务，定时监控商品价格，满足条件就发送邮件
 */
@Component
public class GoodsMonitorTask {

    @Autowired
    private GoodsMonitorService goodsMonitorService;

    public void run(){
        goodsMonitorService.monitorGoodsPrice();
    }

}
