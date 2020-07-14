package me.zhengjie.modules.stock.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.stock.domain.StockMonitor;
import me.zhengjie.modules.stock.service.StockMonitorService;
import me.zhengjie.modules.stock.service.dto.StockMonitorQueryCriteria;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
* @author masterJ
* @date 2020-05-15
*/
@Api(tags = "StockMonitor管理")
@RestController
@RequestMapping("api")
public class StockMonitorController {

    @Autowired
    private StockMonitorService stockMonitorService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${custom.stock.current_time_url}")
    private String stockCurrentTimeUrl;

    @Log("查询StockMonitor")
    @ApiOperation(value = "查询StockMonitor")
    @GetMapping(value = "/stockMonitor")
    @PreAuthorize("hasAnyRole('ADMIN','STOCKMONITOR_ALL','STOCKMONITOR_SELECT')")
    public ResponseEntity getStockMonitors(StockMonitorQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(stockMonitorService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增StockMonitor")
    @ApiOperation(value = "新增StockMonitor")
    @PostMapping(value = "/stockMonitor")
    @PreAuthorize("hasAnyRole('ADMIN','STOCKMONITOR_ALL','STOCKMONITOR_CREATE')")
    public ResponseEntity create(@Validated @RequestBody StockMonitor resources){
        return new ResponseEntity(stockMonitorService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改StockMonitor")
    @ApiOperation(value = "修改StockMonitor")
    @PutMapping(value = "/stockMonitor")
    @PreAuthorize("hasAnyRole('ADMIN','STOCKMONITOR_ALL','STOCKMONITOR_EDIT')")
    public ResponseEntity update(@Validated @RequestBody StockMonitor resources){
        stockMonitorService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除StockMonitor")
    @ApiOperation(value = "删除StockMonitor")
    @DeleteMapping(value = "/stockMonitor/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','STOCKMONITOR_ALL','STOCKMONITOR_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        stockMonitorService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("通过股票代码获取中文名称")
    @ApiOperation(value = "通过股票代码获取中文名称")
    @GetMapping(value = "/stockMonitor/{code}")
    @PreAuthorize("hasAnyRole('ADMIN','STOCKMONITOR_ALL','STOCKMONITOR_DELETE')")
    public ResponseEntity getStockNameByCode(@PathVariable String code) {
        String result = restTemplate.getForObject(stockCurrentTimeUrl + code, String.class);
        result = StringUtils.substringBetween(result,"\"", "\"");
        String[] results = StringUtils.split(result, ",");
        return new ResponseEntity(results.length > 0 ? results[0] : "可能是还没上市的可转债，请自己填入名称", HttpStatus.OK);
    }



}