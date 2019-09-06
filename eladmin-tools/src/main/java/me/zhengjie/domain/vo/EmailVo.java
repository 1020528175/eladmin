package me.zhengjie.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 发送邮件时，接收参数的类
 * @author 郑杰
 * @date 2018/09/28 12:02:14
 */
@Data
@AllArgsConstructor()
@NoArgsConstructor
public class EmailVo {

    /**
     * 收件人，支持多个收件人，用逗号分隔
     */
    @NotEmpty
    private List<String> tos;

    /**
     * 抄送人，支持多个抄送人，用逗号分隔
     */
    private List<String> ccs;

    @NotBlank
    private String subject;

    @NotBlank
    private String content;

    public EmailVo(@NotEmpty List<String> tos, @NotBlank String subject, @NotBlank String content) {
        this.tos = tos;
        this.subject = subject;
        this.content = content;
    }
}
