package me.zhengjie.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Timestamp;

/**
* @author masterJ
* @date 2019-09-17
*/
@Entity
@Data
@Table(name="wechat_config")
public class WechatConfig implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
    * 开发者ID(AppID)
    */
    @Column(name = "app_id",nullable = false)
    @NotBlank
    private String appId;

    /**
    * 令牌(Token)
    */
    @Column(name = "token",nullable = false)
    @NotBlank
    private String token;

    /**
    * 开发者密码(AppSecret)
    */
    @Column(name = "appsecret",nullable = false)
    @NotBlank
    private String appsecret;

    /**
    * 编码：指定属于公众号，还是小程序，还是微信支付
    */
    @Column(name = "code",nullable = false)
    @NotBlank
    private String code;

    @Column(name = "create_by",nullable = false)
    private String createBy;

    @Column(name = "create_date",nullable = false)
    private Timestamp createDate;

    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "update_date")
    private Timestamp updateDate;

    public void copy(WechatConfig source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}