package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.domain.Job;
import me.zhengjie.modules.system.domain.User;
import me.zhengjie.modules.security.security.JwtUser;
import me.zhengjie.modules.system.service.dto.UserDTO;
import me.zhengjie.modules.system.service.dto.UserQueryCriteria;
import me.zhengjie.utils.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 */
@CacheConfig(cacheNames = "user")
public interface UserService {

    /**
     * get
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    UserDTO findById(long id);

    /**
     * create
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    UserDTO create(User resources);

    /**
     * update
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void update(User resources);

    /**
     * delete
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(Long id);

    /**
     * findByName
     * @param userName
     * @return
     */
    @Cacheable(key = "'loadUserByUsername:'+#p0")
    UserDTO findByName(String userName);

    /**
     * findByJob
     * @param job
     * @return
     */
    @Cacheable(key = "'findByJob:'+#p0")
    List<User> findByJob(Job job);

    /**
     * 修改密码
     * @param username
     * @param encryptPassword
     */
    @CacheEvict(allEntries = true)
    void updatePass(String username, String encryptPassword);

    /**
     * 修改头像
     * @param username
     * @param url
     */
    @CacheEvict(allEntries = true)
    void updateAvatar(String username, String url);

    /**
     * 修改邮箱
     * @param username
     * @param email
     */
    @CacheEvict(allEntries = true)
    void updateEmail(String username, String email);

    @Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(UserQueryCriteria criteria, Pageable pageable);

    /**
     * 根据用户工作岗位获取用户邮箱
     * @param stock 股票操盘手
     * @param bond 可转债操盘手
     * @return
     */
    List<String> getEmailByJob(String stock, String bond);

    /**
     * 根据用户工作岗位获取用户邮箱
     * @param stock 股票操盘手
     * @return
     */
    List<String> getEmailByJob(String stock);
}
