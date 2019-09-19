package me.zhengjie.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.zhengjie.annotation.Query;

/**
* @author masterJ
* @date 2019-09-17
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WechatConfigQueryCriteria{

    /**
    * 精确
    */
    @Query
    private String code;
}