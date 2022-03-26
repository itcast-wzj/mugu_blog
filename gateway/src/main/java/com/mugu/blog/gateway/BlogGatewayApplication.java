package com.mugu.blog.gateway;

import com.mugu.blog.gray.config.GrayRuleConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;

@RibbonClients(value ={
        //只对文章服务进行灰度发布
        @RibbonClient(value = "blog-article",configuration = GrayRuleConfig.class)
} )
@SpringBootApplication
public class BlogGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogGatewayApplication.class,args);
    }
}

