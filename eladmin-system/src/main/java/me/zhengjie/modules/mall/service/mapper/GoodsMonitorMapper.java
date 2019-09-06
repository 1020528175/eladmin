package me.zhengjie.modules.mall.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.mall.domain.GoodsMonitor;
import me.zhengjie.modules.mall.service.dto.GoodsMonitorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author masterJ
* @date 2019-09-06
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GoodsMonitorMapper extends EntityMapper<GoodsMonitorDTO, GoodsMonitor> {

}