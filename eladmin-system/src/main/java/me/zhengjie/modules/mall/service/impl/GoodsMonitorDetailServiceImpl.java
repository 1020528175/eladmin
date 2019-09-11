package me.zhengjie.modules.mall.service.impl;

import me.zhengjie.modules.mall.domain.GoodsMonitorDetail;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.modules.mall.repository.GoodsMonitorDetailRepository;
import me.zhengjie.modules.mall.service.GoodsMonitorDetailService;
import me.zhengjie.modules.mall.service.dto.GoodsMonitorDetailDTO;
import me.zhengjie.modules.mall.service.dto.GoodsMonitorDetailQueryCriteria;
import me.zhengjie.modules.mall.service.mapper.GoodsMonitorDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;

/**
* @author masterJ
* @date 2019-09-11
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class GoodsMonitorDetailServiceImpl implements GoodsMonitorDetailService {

    @Autowired
    private GoodsMonitorDetailRepository goodsMonitorDetailRepository;

    @Autowired
    private GoodsMonitorDetailMapper goodsMonitorDetailMapper;

    @Override
    public Object queryAll(GoodsMonitorDetailQueryCriteria criteria, Pageable pageable){
        Page<GoodsMonitorDetail> page = goodsMonitorDetailRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(goodsMonitorDetailMapper::toDto));
    }

    @Override
    public Object queryAll(GoodsMonitorDetailQueryCriteria criteria){
        return goodsMonitorDetailMapper.toDto(goodsMonitorDetailRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public GoodsMonitorDetailDTO findById(Long id) {
        Optional<GoodsMonitorDetail> goodsMonitorDetail = goodsMonitorDetailRepository.findById(id);
        ValidationUtil.isNull(goodsMonitorDetail,"GoodsMonitorDetail","id",id);
        return goodsMonitorDetailMapper.toDto(goodsMonitorDetail.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GoodsMonitorDetailDTO create(GoodsMonitorDetail resources) {
        return goodsMonitorDetailMapper.toDto(goodsMonitorDetailRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(GoodsMonitorDetail resources) {
        Optional<GoodsMonitorDetail> optionalGoodsMonitorDetail = goodsMonitorDetailRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalGoodsMonitorDetail,"GoodsMonitorDetail","id",resources.getId());
        GoodsMonitorDetail goodsMonitorDetail = optionalGoodsMonitorDetail.get();
        goodsMonitorDetail.copy(resources);
        goodsMonitorDetailRepository.save(goodsMonitorDetail);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        goodsMonitorDetailRepository.deleteById(id);
    }
}