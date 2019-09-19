package me.zhengjie.modules.mall.repository;

import me.zhengjie.modules.mall.domain.GoodsMonitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
* @author masterJ
* @date 2019-09-06
*/
public interface GoodsMonitorRepository extends JpaRepository<GoodsMonitor, Long>, JpaSpecificationExecutor {

    /**
     * 修改商品监控的开启状态
     * @param id
     * @param openStatus
     */
    @Modifying()
    @Query("update GoodsMonitor set openStatus = ?2 where id = ?1")
    void updateOpenStatusById(Long id, boolean openStatus);
}