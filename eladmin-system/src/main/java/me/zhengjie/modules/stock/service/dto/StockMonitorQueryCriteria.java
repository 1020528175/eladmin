package me.zhengjie.modules.stock.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.math.BigDecimal;
import me.zhengjie.annotation.Query;

/**
* @author masterJ
* @date 2020-05-15
*/
@Data
public class StockMonitorQueryCriteria{

    /**
    * 精确
    */
    @Query
    private Long id;

    /**
    * 模糊
    */
    @Query(type = Query.Type.INNER_LIKE)
    private String code;

    /**
    * 精确
    */
    @Query
    private String type;
}