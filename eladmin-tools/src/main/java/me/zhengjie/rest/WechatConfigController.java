package me.zhengjie.rest;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.aop.log.Log;
import me.zhengjie.domain.WechatConfig;
import me.zhengjie.service.WechatConfigService;
import me.zhengjie.service.dto.WechatConfigQueryCriteria;
import me.zhengjie.utils.EncryptUtils;
import me.zhengjie.utils.WeChatUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
* @author masterJ
* @date 2019-09-17
*/
@Api(tags = "WechatConfig管理")
@RestController
@RequestMapping("api")
@Slf4j
public class WechatConfigController {

    @Autowired
    private WechatConfigService wechatConfigService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WeChatUtil weChatUtil;

    @Log("查询WechatConfig")
    @ApiOperation(value = "查询WechatConfig")
    @GetMapping(value = "/wechatConfig")
    @PreAuthorize("hasAnyRole('ADMIN','WECHATCONFIG_ALL','WECHATCONFIG_SELECT')")
    public ResponseEntity getWechatConfigs(WechatConfigQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(wechatConfigService.findOne(criteria),HttpStatus.OK);
    }

    @Log("修改WechatConfig")
    @ApiOperation(value = "修改WechatConfig")
    @PutMapping(value = "/wechatConfig")
    @PreAuthorize("hasAnyRole('ADMIN','WECHATCONFIG_ALL','WECHATCONFIG_EDIT')")
    public ResponseEntity update(@Validated @RequestBody WechatConfig resources){
        wechatConfigService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    /**
     * 接入微信公众号的验证
     * @param request
     * @return
     */
    @Log("接入微信公众号的验证")
    @GetMapping("/weChat/checkToken")
    public ResponseEntity getToken(HttpServletRequest request) {
        WechatConfig wechatConfig = wechatConfigService.findOne(new WechatConfigQueryCriteria("subscription"));
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        String[] arrays = {wechatConfig.getToken(), timestamp, nonce};
        Arrays.sort(arrays);

        StringBuffer stringBuffer = new StringBuffer();
        List<String> list = Arrays.asList(arrays);
        for (String string : list) {
            stringBuffer.append(string);
        }
        String hash = EncryptUtils.sha1(stringBuffer.toString());
        if (hash.equals(signature)) {
            return new ResponseEntity(echostr, HttpStatus.OK);
        }
        return new ResponseEntity("认证失败！", HttpStatus.OK);
    }


    /**
     * 创建公众号菜单
     * 微信创建菜单接口
     * https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN
     * @return
     * @throws Exception
     */
    @GetMapping("/weChat/createMenu")
    @ResponseBody
    public ResponseEntity createWeChatMenu(String menuJson) throws Exception {
        if (StringUtils.isBlank(menuJson)){
            @Cleanup InputStream inputStream  = Thread.currentThread().getContextClassLoader().getResource("wechat-menu-example/weChat_menu.json").openStream();
            byte[] filecontent = new byte[inputStream.available()];
            inputStream.read(filecontent);
            menuJson = new String(filecontent,"utf-8");
        }
        log.info("创建菜单，menuJson====" + menuJson);
        JSONObject jsonObject = restTemplate.postForObject(String.format("https://api.weixin.qq.com/cgi-bin/menu/create?access_token=%s",weChatUtil.getAccessToken()), menuJson,JSONObject.class);
        return new ResponseEntity(jsonObject.toJSONString(),HttpStatus.OK);
    }

}