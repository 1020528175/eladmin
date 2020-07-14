package me.zhengjie.modules.stock.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;


/**
* @author masterJ
* @date 2020-05-15
*/
@Data
public class StockMonitorDTO implements Serializable {

    private Long id;

    /**
    * 代码
    */
    private String code;

    /**
    * 名称
    */
    private String name;

    /**
    * 最高监控价
    */
    private BigDecimal maxPrice;

    /**
    * 最低监控价
    */
    private BigDecimal minPrice;

    /**
    * 当日下跌幅度监控百分比，% 不保存
    */
    private BigDecimal fallRate;

    /**
    * STOCK：股票，ETF：etf基金，BOND:可以转换债券,INDEX：指数
    */
    private String type;

    private String createBy;

    private Timestamp createDate;

    private String updateBy;

    private Timestamp updateDate;
}