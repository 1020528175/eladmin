package me.zhengjie.modules.mall.repository;

import me.zhengjie.modules.mall.domain.GoodsMonitorDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author masterJ
* @date 2019-09-11
*/
public interface GoodsMonitorDetailRepository extends JpaRepository<GoodsMonitorDetail, Long>, JpaSpecificationExecutor {
}