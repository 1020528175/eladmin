package me.zhengjie.modules.stock.service;

import me.zhengjie.modules.stock.domain.StockMonitor;
import me.zhengjie.modules.stock.service.dto.StockMonitorDTO;
import me.zhengjie.modules.stock.service.dto.StockMonitorQueryCriteria;
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.hibernate.validator.constraints.EAN;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
* @author masterJ
* @date 2020-05-15
*/
@CacheConfig(cacheNames = "stockMonitor")
public interface StockMonitorService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    @Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(StockMonitorQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    @Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(StockMonitorQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    StockMonitorDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    StockMonitorDTO create(StockMonitor resources);

    /**
     * update
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void update(StockMonitor resources);

    /**
     * delete
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(Long id);

    /**
     * 根据类型监控
     * @param type
     */
    void monitor(String type);

    List<StockMonitor> findByType(String type);
}