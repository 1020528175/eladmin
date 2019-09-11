package me.zhengjie.modules.mall.service;

import me.zhengjie.modules.mall.domain.GoodsMonitorDetail;
import me.zhengjie.modules.mall.service.dto.GoodsMonitorDetailDTO;
import me.zhengjie.modules.mall.service.dto.GoodsMonitorDetailQueryCriteria;
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

/**
* @author masterJ
* @date 2019-09-11
*/
//@CacheConfig(cacheNames = "goodsMonitorDetail")
public interface GoodsMonitorDetailService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(GoodsMonitorDetailQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    //@Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(GoodsMonitorDetailQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    GoodsMonitorDetailDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    GoodsMonitorDetailDTO create(GoodsMonitorDetail resources);

    /**
     * update
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    void update(GoodsMonitorDetail resources);

    /**
     * delete
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Long id);
}