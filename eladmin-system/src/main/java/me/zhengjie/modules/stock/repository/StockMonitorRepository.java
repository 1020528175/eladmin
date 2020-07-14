package me.zhengjie.modules.stock.repository;

import me.zhengjie.modules.stock.domain.StockMonitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
* @author masterJ
* @date 2020-05-15
*/
public interface StockMonitorRepository extends JpaRepository<StockMonitor, Long>, JpaSpecificationExecutor {

    List<StockMonitor> findByType(String type);
}