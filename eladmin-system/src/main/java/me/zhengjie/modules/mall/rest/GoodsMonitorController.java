package me.zhengjie.modules.mall.rest;

import cn.hutool.core.util.BooleanUtil;
import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.mall.domain.GoodsMonitor;
import me.zhengjie.modules.mall.service.GoodsMonitorService;
import me.zhengjie.modules.mall.service.dto.GoodsMonitorQueryCriteria;
import org.apache.commons.lang3.BooleanUtils;
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
* @date 2019-09-06
*/
@Api(tags = "GoodsMonitor管理")
@RestController
@RequestMapping("api")
public class GoodsMonitorController {

    @Autowired
    private GoodsMonitorService goodsMonitorService;

    @Log("查询GoodsMonitor")
    @ApiOperation(value = "查询GoodsMonitor")
    @GetMapping(value = "/goodsMonitor")
    @PreAuthorize("hasAnyRole('ADMIN','GOODSMONITOR_ALL','GOODSMONITOR_SELECT')")
    public ResponseEntity getGoodsMonitors(GoodsMonitorQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(goodsMonitorService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增GoodsMonitor")
    @ApiOperation(value = "新增GoodsMonitor")
    @PostMapping(value = "/goodsMonitor")
    @PreAuthorize("hasAnyRole('ADMIN','GOODSMONITOR_ALL','GOODSMONITOR_CREATE')")
    public ResponseEntity create(@Validated @RequestBody GoodsMonitor resources){
        return new ResponseEntity(goodsMonitorService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改GoodsMonitor")
    @ApiOperation(value = "修改GoodsMonitor")
    @PutMapping(value = "/goodsMonitor")
    @PreAuthorize("hasAnyRole('ADMIN','GOODSMONITOR_ALL','GOODSMONITOR_EDIT')")
    public ResponseEntity update(@Validated(GoodsMonitor.Update.class) @RequestBody GoodsMonitor resources){
        goodsMonitorService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除GoodsMonitor")
    @ApiOperation(value = "删除GoodsMonitor")
    @DeleteMapping(value = "/goodsMonitor/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','GOODSMONITOR_ALL','GOODSMONITOR_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        goodsMonitorService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("查询GoodsInfo")
    @ApiOperation(value = "查询GoodsInfo")
    @GetMapping(value = "/getGoodsInfo")
    @PreAuthorize("hasAnyRole('ADMIN','GOODSMONITOR_ALL','GOODSMONITOR_SELECT')")
    public ResponseEntity getGoodsInfo(String link){
        System.out.println("link = " + link);
        GoodsMonitor goodsMonitor = new GoodsMonitor();
        goodsMonitor.setLink(link);
        return new ResponseEntity(goodsMonitorService.getGoodsInfo(goodsMonitor),HttpStatus.OK);
    }

}