package com.mugu.blog.social;

import com.mugu.blog.social.github.GithubSocialApI;
import com.mugu.blog.social.model.SecuritySocialProperties;
import com.mugu.blog.social.model.SocialConstant;
import com.mugu.blog.social.qq.QQSocialApI;
import com.mugu.blog.social.service.SocialApI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author 公众号：码猿技术专栏
 * @url: www.java-family.cn
 * @description 社交登录的配置
 */
@Configuration
@ComponentScan
@EnableConfigurationProperties(value = {SecuritySocialProperties.class})
public class SecuritySocialAutoConfig {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 注入并配置RestTemplate
     */
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate(simpleClientHttpRequestFactory());
        restTemplate.setMessageConverters(getMessageConverters());
        return restTemplate;
    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(5000);//单位为ms
        factory.setConnectTimeout(5000);//单位为ms
        return factory;
    }

    /**
     * 注入GitHub社交登录的实现接口
     */
    @ConditionalOnProperty(prefix = "security.social.github",name = "enable",havingValue = "true",matchIfMissing = false)
    @Bean(value = "githubSocialApI")
    public SocialApI githubSocialApI(RestTemplate restTemplate,SecuritySocialProperties socialProperties){
        return new GithubSocialApI(SocialConstant.GITHUB_ACCESS_TOKEN_URL,SocialConstant.GITHUB_USER_INFO_URL,restTemplate,stringRedisTemplate,socialProperties);
    }

    /**
     * 注入QQ社交登录的实现接口
     */
    @ConditionalOnProperty(prefix = "security.social.qq",name = "enable",havingValue = "true",matchIfMissing = false)
    @Bean(value = "qqSocialApI")
    public SocialApI qqSocialApI(RestTemplate restTemplate,SecuritySocialProperties socialProperties){
        return new QQSocialApI(SocialConstant.QQ_OPENID_URL,SocialConstant.QQ_USER_INFO_URL,SocialConstant.ACCESS_TOKEN_URL,restTemplate,stringRedisTemplate,socialProperties);
    }

    /**
     * 自定义适配的HttpMessageConverter
     */
    private List<HttpMessageConverter<?>> getMessageConverters() {
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        messageConverters.add(new StringHttpMessageConverter());
        messageConverters.add(getFormMessageConverter());
        messageConverters.add(getJsonMessageConverter());
        messageConverters.add(getByteArrayMessageConverter());
        return messageConverters;
    }

    private FormHttpMessageConverter getFormMessageConverter() {
        FormHttpMessageConverter converter = new FormHttpMessageConverter();
        converter.setCharset(StandardCharsets.UTF_8);
        List<HttpMessageConverter<?>> partConverters = new ArrayList<HttpMessageConverter<?>>();
        partConverters.add(new ByteArrayHttpMessageConverter());
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        stringHttpMessageConverter.setWriteAcceptCharset(false);
        partConverters.add(stringHttpMessageConverter);
        partConverters.add(new ResourceHttpMessageConverter());
        converter.setPartConverters(partConverters);
        return converter;
    }

    private MappingJackson2HttpMessageConverter getJsonMessageConverter() {
        return new MappingJackson2HttpMessageConverter();
    }

    private ByteArrayHttpMessageConverter getByteArrayMessageConverter() {
        ByteArrayHttpMessageConverter converter = new ByteArrayHttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.IMAGE_JPEG, MediaType.IMAGE_GIF, MediaType.IMAGE_PNG));
        return converter;
    }

}
