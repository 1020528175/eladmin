package me.zhengjie.service.impl;

import cn.hutool.extra.mail.Mail;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.domain.EmailConfig;
import me.zhengjie.domain.vo.EmailVo;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.repository.EmailRepository;
import me.zhengjie.service.EmailService;
import me.zhengjie.utils.ElAdminConstant;
import me.zhengjie.utils.EncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

/**
 * @author Zheng Jie
 * @date 2018-12-26
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class EmailServiceImpl implements EmailService {

    @Value("${email.spam}")
    private String[] spams;

    @Autowired
    private EmailRepository emailRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public EmailConfig update(EmailConfig emailConfig, EmailConfig old) {
        try {
            if(!emailConfig.getPass().equals(old.getPass())){
                // 对称加密
                emailConfig.setPass(EncryptUtils.desEncrypt(emailConfig.getPass()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emailRepository.save(emailConfig);
    }

    @Override
    public EmailConfig find() {
        Optional<EmailConfig> emailConfig = emailRepository.findById(1L);
        if(emailConfig.isPresent()){
            return emailConfig.get();
        } else {
            return new EmailConfig();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void send(EmailVo emailVo, EmailConfig emailConfig){
        if(emailConfig == null || emailConfig.getId() == null){
            throw new BadRequestException("请先配置发件人邮箱，再操作");
        }
        /**
         * 封装
         */
        MailAccount account = new MailAccount();
        account.setHost(emailConfig.getHost());
        account.setPort(Integer.parseInt(emailConfig.getPort()));
        account.setAuth(true);
        try {
            // 对称解密
            account.setPass(EncryptUtils.desDecrypt(emailConfig.getPass()));
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
        account.setFrom(emailConfig.getUser()+"<"+emailConfig.getFromUser()+">");
        //ssl方式发送
        account.setStartttlsEnable(true);
        String content = emailVo.getContent();
        /**
         * 发送
         */
        try {
            Mail.create(account)
                    .setTos(emailVo.getTos().toArray(new String[emailVo.getTos().size()]))
                    .setCcs(emailVo.getCcs().toArray(new String[emailVo.getCcs().size()]))
                    .setTitle(emailVo.getSubject())
                    .setContent(content)
                    .setHtml(true)
                    //关闭session
                    .setUseGlobalSession(false)
                    .send();
        }catch (Exception e){
            //me.zhengjie.exception.BadRequestException: SMTPSendFailedException: 554 DT:SPM 163 smtp11,D8CowAB3j7dvcXBd5GkKCA--.59250S2 1567650170,please see http://mail.163.com/help/help_spam_16.htm?ip=171.212.221.60&hostid=smtp11&time=1567650170
            //有可能会当成垃圾邮件而发送不出去，所以去掉了接口上的@Async注解，使发送邮件为同步发送
            for (String spam : spams) {
                if (e.getMessage().contains(spam)){
                    //当成垃圾邮件了
                    log.error(e.getMessage());
                    throw new RuntimeException("该邮件被识别为垃圾邮件，请勾选抄送自己，再重试");
                }
            }

            throw new RuntimeException(e.getMessage());
        }
    }
}
