package me.zhengjie.modules.mall.service.dto;

import lombok.Data;
import me.zhengjie.modules.mall.domain.GoodsMonitor;

import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;


/**
* @author masterJ
* @date 2019-09-11
*/
@Data
public class GoodsMonitorDetailDTO implements Serializable {

    private Long id;

    /**
    * 商品
    */
    private GoodsMonitor goodsMonitor;

    /**
    * 当前价格
    */
    private BigDecimal price;

    /**
    * 监控时间
    */
    private Timestamp createDate;

    /**
    * 高于最高监控价
    */
    private boolean sendMaxEmail;

    /**
    * 低于最低监控价
    */
    private boolean sendMinEmail;
}