package me.zhengjie.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.domain.WechatConfig;
import me.zhengjie.service.WechatConfigService;
import me.zhengjie.service.dto.WechatConfigQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

/**
* @author masterJ
* @date 2019-09-17
*/
@Api(tags = "WechatConfig管理")
@RestController
@RequestMapping("api")
public class WechatConfigController {

    @Autowired
    private WechatConfigService wechatConfigService;

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

}