package com.mugu.blog.feign;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ComponentScan
public class FeignAutoConfig {

    /**
     * 开启openFeign的日志增强，生产环境中关闭
     * @return
     */
    @Profile(value = {"dev","test"})
    @Bean
    public Logger.Level level(){
        return Logger.Level.FULL;
    }
}
