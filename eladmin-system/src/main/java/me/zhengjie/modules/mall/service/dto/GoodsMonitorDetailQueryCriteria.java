package me.zhengjie.modules.mall.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.math.BigDecimal;
import me.zhengjie.annotation.Query;
import me.zhengjie.modules.mall.domain.GoodsMonitor;

/**
* @author masterJ
* @date 2019-09-11
*/
@Data
public class GoodsMonitorDetailQueryCriteria{

    // 精确
    @Query
    private Long id;

    // 模糊
//    @Query(type = Query.Type.INNER_LIKE)
//    private GoodsMonitor goodsMonitor

    // 精确
    @Query
    private Timestamp createDate;

    // 精确
    @Query
    private Boolean sendMaxEmail;

    // 精确
    @Query
    private Boolean sendMinEmail;
}