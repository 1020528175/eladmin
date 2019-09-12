package me.zhengjie.modules.mall.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;

/**
* @author masterJ
* @date 2019-09-11
*/
@Entity
@Data
@Table(name="mall_goods_monitor_detail")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsMonitorDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull(groups = Update.class)
    private Long id;

    /**
    * 商品标题
     * @Column(name = "gm_id",nullable = false)  关联表不能使用@Column注解
    */
    @JoinColumn(name = "gm_id",nullable = false)
    @ManyToOne
    private GoodsMonitor goodsMonitor;

    /**
    * 当前价格
    */
    @Column(name = "price",nullable = false)
    @NotNull
    private BigDecimal price;

    /**
    * 监控时间
    */
    @Column(name = "create_date",nullable = false)
    @NotNull
    private Timestamp createDate;

    /**
    * 高于最高监控价
    */
    @Column(name = "send_max_email",nullable = false)
    private boolean sendMaxEmail;

    /**
    * 低于最低监控价
    */
    @Column(name = "send_min_email",nullable = false)
    private boolean sendMinEmail;

    public void copy(GoodsMonitorDetail source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }

    public @interface Update{}
}