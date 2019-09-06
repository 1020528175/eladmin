package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.domain.Dept;
import me.zhengjie.modules.system.service.dto.DeptDTO;
import me.zhengjie.modules.system.service.dto.DeptQueryCriteria;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Set;

/**
* @author Zheng Jie
* @date 2019-03-25
*/
@CacheConfig(cacheNames = "dept")
public interface DeptService {

    /**
     * queryAll
     * @param criteria
     * @return
     *  类上配置的value/cacheNames,指定的是这个类的所有带缓存注解的方法，缓存存放在哪块命名空间,如果方法上也指定了value/cacheNames，那这个方法就以方法上配置的作为这个方法缓存的命名空间
     *  最后保存到redis的缓存key就是：命名空间::keyGenerator生成的结果   组成缓存的key
     *  如:  dept::me.zhengjie.modules.system.service.impl.DeptServiceImplqueryAll467849810
     *  keyGenerator = "keyGenerator",就是指定这个方法的缓存key的生成策略是keyGenerator，keyGenerator就是KeyGenerator(函数式接口)的实现类，在RedisConfig里面实例化了KeyGenerator的实现类
     */
    @Cacheable(keyGenerator = "keyGenerator")
    List<DeptDTO> queryAll(DeptQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    DeptDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    DeptDTO create(Dept resources);

    /**
     * update
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void update(Dept resources);

    /**
     * delete
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(Long id);

    /**
     * buildTree
     * @param deptDTOS
     * @return
     */
    @Cacheable(keyGenerator = "keyGenerator")
    Object buildTree(List<DeptDTO> deptDTOS);

    /**
     * findByPid
     * @param pid
     * @return
     */
    @Cacheable(keyGenerator = "keyGenerator")
    List<Dept> findByPid(long pid);

    Set<Dept> findByRoleIds(Long id);
}