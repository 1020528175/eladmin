package me.zhengjie.service.impl;

import me.zhengjie.domain.WechatConfig;
import me.zhengjie.repository.WechatConfigRepository;
import me.zhengjie.service.WechatConfigService;
import me.zhengjie.service.dto.WechatConfigQueryCriteria;
import me.zhengjie.utils.QueryHelp;
import me.zhengjie.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
* @author masterJ
* @date 2019-09-17
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class WechatConfigServiceImpl implements WechatConfigService {

    @Autowired
    private WechatConfigRepository wechatConfigRepository;


    @Override
    public WechatConfig findOne(WechatConfigQueryCriteria criteria){
        Optional<WechatConfig> optional = wechatConfigRepository.findOne((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder));
        return optional.isPresent() ? optional.get() : new WechatConfig();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(WechatConfig resources) {
        if (resources.getId() == null){
            resources.setCreateBy(SecurityUtils.getUsername());
            resources.setCreateDate(new Timestamp(System.currentTimeMillis()));
        }else {
            resources.setUpdateBy(SecurityUtils.getUsername());
            resources.setUpdateDate(new Timestamp(System.currentTimeMillis()));
        }
        wechatConfigRepository.save(resources);
    }

}