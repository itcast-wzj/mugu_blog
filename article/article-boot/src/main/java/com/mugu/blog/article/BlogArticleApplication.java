package com.mugu.blog.article;

import com.mugu.blog.gray.config.GrayRuleConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@MapperScan(value = "com.mugu.blog.article.dao")
@EnableElasticsearchRepositories(basePackages = {"com.mugu.blog.article.es.dao"})
//defaultConfiguration 指定对所有feign的服务调用都使用灰度发布的规则
//@RibbonClients(defaultConfiguration = {GrayRuleConfig.class})
@RibbonClients(value = {
        //指定对blog-comments这个服务开启灰度部署
        @RibbonClient(value = "blog-comments",configuration = GrayRuleConfig.class)
})
public class BlogArticleApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogArticleApplication.class,args);
    }
}
