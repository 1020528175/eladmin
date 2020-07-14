package me.zhengjie.modules.stock.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.stock.domain.StockMonitor;
import me.zhengjie.modules.stock.service.dto.StockMonitorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author masterJ
* @date 2020-05-15
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StockMonitorMapper extends EntityMapper<StockMonitorDTO, StockMonitor> {

}