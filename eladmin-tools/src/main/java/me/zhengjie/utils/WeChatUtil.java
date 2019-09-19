package me.zhengjie.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.domain.WechatConfig;
import me.zhengjie.service.WechatConfigService;
import me.zhengjie.service.dto.WechatConfigQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author master
 * @create 2019-09-18 10:42
 */
@Component
@Slf4j
public class WeChatUtil {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WechatConfigService wechatConfigService;

    /**
     * 获取微信授权的access_token
     * 微信获取access_token  接口
     * https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
     * @return  access_token
     */
    public String getAccessToken() {
        WechatConfig wechatConfig = wechatConfigService.findOne(new WechatConfigQueryCriteria("subscription"));
        String url = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s",wechatConfig.getAppId(),wechatConfig.getAppsecret());
        //String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + wechatConfig.getAppId() + "&secret=" + wechatConfig.getAppsecret();
        JSONObject jsonObject = restTemplate.getForObject(url,JSONObject.class);
        return jsonObject.get("access_token").toString();
    }
}
