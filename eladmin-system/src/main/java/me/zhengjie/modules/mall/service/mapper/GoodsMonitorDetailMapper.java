package me.zhengjie.modules.mall.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.mall.domain.GoodsMonitorDetail;
import me.zhengjie.modules.mall.service.dto.GoodsMonitorDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author masterJ
* @date 2019-09-11
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GoodsMonitorDetailMapper extends EntityMapper<GoodsMonitorDetailDTO, GoodsMonitorDetail> {

}