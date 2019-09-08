package me.zhengjie.modules.mall.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
* @author masterJ
* @date 2019-09-06
*/
@Data
public class GoodsMonitorDTO implements Serializable {

    /**
    * 序号
    */
    private Long id;

    /**
    * 商品标题
    */
    private String title;

    /**
    * 商品链接
    */
    private String link;

    /**
    * 商品首图
    */
    private String imgUrl;

    /**
    * 商城
    */
    private String originMall;

    /**
    * 最高价
    */
    private BigDecimal maxPrice;

    /**
    * 最低价
    */
    private BigDecimal minPrice;

    /**
    * 开启状态
    */
    private boolean openStatus;

    /**
    * 邮件地址
    */
    private String email;

    /**
    * 删除状态
    */
    private boolean deleteStatus;

    /**
    * 创建人
    */
    private String createBy;

    /**
    * 创建时间
    */
    private Timestamp createDate;

    /**
    * 更新人
    */
    private String updateBy;

    /**
    * 更新时间
    */
    private Timestamp updateDate;
}