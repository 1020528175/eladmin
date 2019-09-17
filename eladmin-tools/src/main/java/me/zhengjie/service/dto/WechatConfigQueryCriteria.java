package me.zhengjie.service.dto;

import lombok.Data;
import me.zhengjie.annotation.Query;

/**
* @author masterJ
* @date 2019-09-17
*/
@Data
public class WechatConfigQueryCriteria{

    /**
    * 精确
    */
    @Query
    private String code;
}