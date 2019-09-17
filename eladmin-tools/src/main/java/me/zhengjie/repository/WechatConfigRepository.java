package me.zhengjie.repository;

import me.zhengjie.domain.WechatConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author masterJ
* @date 2019-09-17
*/
public interface WechatConfigRepository extends JpaRepository<WechatConfig, Long>, JpaSpecificationExecutor {
}