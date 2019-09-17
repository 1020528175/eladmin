package me.zhengjie.service;

import me.zhengjie.domain.WechatConfig;
import me.zhengjie.service.dto.WechatConfigQueryCriteria;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;


/**
* @author masterJ
* @date 2019-09-17
*/
@CacheConfig(cacheNames = "wechatConfig")
public interface WechatConfigService {


    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    @Cacheable(keyGenerator = "keyGenerator")
    WechatConfig findOne(WechatConfigQueryCriteria criteria);


    /**
     * update
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void update(WechatConfig resources);

}