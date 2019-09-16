package me.zhengjie.modules.mall.service;

import me.zhengjie.modules.mall.domain.GoodsMonitor;
import me.zhengjie.modules.mall.service.dto.GoodsMonitorDTO;
import me.zhengjie.modules.mall.service.dto.GoodsMonitorQueryCriteria;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

/**
* @author masterJ
* @date 2019-09-06
*/
@CacheConfig(cacheNames = "goodsMonitor")
public interface GoodsMonitorService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    @Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(GoodsMonitorQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    @Cacheable(keyGenerator = "keyGenerator")
    List<GoodsMonitorDTO> queryAll(GoodsMonitorQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    GoodsMonitorDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    GoodsMonitorDTO create(GoodsMonitor resources);

    /**
     * update
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void update(GoodsMonitor resources);

    /**
     * delete
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(Long id);

    /**
     * 通过商品链接，获取商品的相关信息
     * @param goodsMonitor
     * @return
     */
    @Cacheable(key = "#goodsMonitor.link")
    GoodsMonitor getGoodsInfo(GoodsMonitor goodsMonitor);

    /**
     * 通过商品链接，获取商品的当前价格
     * @param link 商品链接
     * @return
     */
    BigDecimal getCurrentPrice(String link);

    /**
     * 监控商品价格变动
     * @return
     */
    void monitorGoodsPrice();
}