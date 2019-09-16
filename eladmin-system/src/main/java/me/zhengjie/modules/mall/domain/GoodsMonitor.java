package me.zhengjie.modules.mall.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;

/**
* @author masterJ
* @date 2019-09-06
*/
@Entity
@Data
@Table(name="mall_goods_monitor")
@AllArgsConstructor
@NoArgsConstructor
public class GoodsMonitor implements Serializable {

    /**
     * 序号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull(groups = {Update.class})
    private Long id;

    /**
    * 商品标题
    */
    @Column(name = "title",nullable = false)
    @NotBlank
    private String title;

    /**
    * 商品链接
    */
    @Column(name = "link",nullable = false)
    @NotBlank
    private String link;

    /**
    * 商品首图
    */
    @Column(name = "img_url",nullable = false)
    @NotBlank
    private String imgUrl;

    /**
    * 商城
    */
    @Column(name = "origin_mall",nullable = false)
    @NotBlank
    private String originMall;

    /**
    * 现价
    */
    @Column(name = "current_price")
    private BigDecimal currentPrice;

    /**
    * 最高价
    */
    @Column(name = "max_price")
    private BigDecimal maxPrice;

    /**
    * 最低价
    */
    @Column(name = "min_price",nullable = false)
    @NotNull
    private BigDecimal minPrice;

    /**
    * 开启状态
    */
    @Column(name = "open_status",nullable = false)
    private boolean openStatus;

    /**
    * 邮件地址,支持多个邮件，用‘,’隔开，这里就只验证必填了，也可以自定义规则验证
    */
    @Column(name = "email",nullable = false)
    @NotBlank
    private String email;

    /**
    * 删除状态
    */
    @Column(name = "delete_status",nullable = false)
    private boolean deleteStatus;

    /**
    * 创建人
    */
    @Column(name = "create_by",nullable = false)
    private String createBy;

    /**
    * 创建时间
    */
    @Column(name = "create_date",nullable = false)
    private Timestamp createDate;

    /**
    * 更新人
    */
    @Column(name = "update_by")
    private String updateBy;

    /**
    * 更新时间
    */
    @Column(name = "update_date")
    private Timestamp updateDate;

    public GoodsMonitor(Long id) {
        this.id = id;
    }

    public void copy(GoodsMonitor source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }

    public @interface Update{}
}