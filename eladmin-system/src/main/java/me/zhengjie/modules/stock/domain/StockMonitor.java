package me.zhengjie.modules.stock.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;

/**
* @author masterJ
* @date 2020-05-15
*/
@Entity
@Data
@Table(name="stock_monitor")
public class StockMonitor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
    * 代码
    */
    @Column(name = "code",nullable = false)
    private String code;

    /**
    * 名称
    */
    @Column(name = "name",nullable = false)
    private String name;

    /**
    * 最高监控价
    */
    @Column(name = "max_price")
    private BigDecimal maxPrice;

    /**
    * 最低监控价
    */
    @Column(name = "min_price")
    private BigDecimal minPrice;

    /**
    * 当日下跌幅度监控百分比，% 不保存
    */
    @Column(name = "fall_rate")
    private BigDecimal fallRate;

    /**
    * STOCK：股票，ETF：etf基金，BOND:可以转换债券,INDEX：指数
    */
    @Column(name = "type")
    private String type;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "create_date")
    private Timestamp createDate;

    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "update_date")
    private Timestamp updateDate;

    public void copy(StockMonitor source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(false));
    }
}