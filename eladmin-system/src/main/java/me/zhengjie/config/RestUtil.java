package me.zhengjie.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author master
 * @create 2019-09-06 15:21
 */
@Configuration
public class RestUtil {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
