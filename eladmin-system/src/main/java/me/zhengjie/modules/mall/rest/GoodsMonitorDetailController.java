package me.zhengjie.modules.mall.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.mall.domain.GoodsMonitorDetail;
import me.zhengjie.modules.mall.service.GoodsMonitorDetailService;
import me.zhengjie.modules.mall.service.dto.GoodsMonitorDetailQueryCriteria;
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
* @date 2019-09-11
*/
@Api(tags = "GoodsMonitorDetail管理")
@RestController
@RequestMapping("api")
public class GoodsMonitorDetailController {

    @Autowired
    private GoodsMonitorDetailService goodsMonitorDetailService;

    @Log("查询GoodsMonitorDetail")
    @ApiOperation(value = "查询GoodsMonitorDetail")
    @GetMapping(value = "/goodsMonitorDetail")
    @PreAuthorize("hasAnyRole('ADMIN','GOODSMONITORDETAIL_ALL','GOODSMONITORDETAIL_SELECT')")
    public ResponseEntity getGoodsMonitorDetails(GoodsMonitorDetailQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(goodsMonitorDetailService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增GoodsMonitorDetail")
    @ApiOperation(value = "新增GoodsMonitorDetail")
    @PostMapping(value = "/goodsMonitorDetail")
    @PreAuthorize("hasAnyRole('ADMIN','GOODSMONITORDETAIL_ALL','GOODSMONITORDETAIL_CREATE')")
    public ResponseEntity create(@Validated @RequestBody GoodsMonitorDetail resources){
        return new ResponseEntity(goodsMonitorDetailService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改GoodsMonitorDetail")
    @ApiOperation(value = "修改GoodsMonitorDetail")
    @PutMapping(value = "/goodsMonitorDetail")
    @PreAuthorize("hasAnyRole('ADMIN','GOODSMONITORDETAIL_ALL','GOODSMONITORDETAIL_EDIT')")
    public ResponseEntity update(@Validated @RequestBody GoodsMonitorDetail resources){
        goodsMonitorDetailService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除GoodsMonitorDetail")
    @ApiOperation(value = "删除GoodsMonitorDetail")
    @DeleteMapping(value = "/goodsMonitorDetail/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','GOODSMONITORDETAIL_ALL','GOODSMONITORDETAIL_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        goodsMonitorDetailService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}