package me.zhengjie.modules.mall.repository;

import me.zhengjie.modules.mall.domain.GoodsMonitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author masterJ
* @date 2019-09-06
*/
public interface GoodsMonitorRepository extends JpaRepository<GoodsMonitor, Long>, JpaSpecificationExecutor {
}