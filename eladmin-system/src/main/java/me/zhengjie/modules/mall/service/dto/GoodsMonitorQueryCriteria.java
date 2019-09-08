package me.zhengjie.modules.mall.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.sql.Timestamp;
import java.math.BigDecimal;

import lombok.NoArgsConstructor;
import me.zhengjie.annotation.Query;

/**
* @author masterJ
* @date 2019-09-06
*/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsMonitorQueryCriteria{
    /**
     * 开启状态
     */
    @Query
    private boolean openStatus;

    /**
     * 删除状态
     */
    @Query
    private boolean deleteStatus;


    /**
     * 商品标题
     */
    @Query
    private String title;

}