package com.mugu.blog.social.service;

import com.mugu.blog.social.model.SecuritySocialProperties;
import com.mugu.blog.social.model.UserConnection;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author 公众号：码猿技术专栏
 * @url: www.java-family.cn
 * @description 获取服务商信息的抽象类，对SocialApI中的接口做了一定的封装
 */
public abstract class AbstractSocialApI implements SocialApI{

    /**
     * 获取令牌的url
     */
    private  String accessTokenUrl;

    private RestTemplate restTemplate;

    private StringRedisTemplate stringRedisTemplate;

    private SecuritySocialProperties socialProperties;

    public AbstractSocialApI(String accessTokenUrl,RestTemplate restTemplate,StringRedisTemplate stringRedisTemplate,SecuritySocialProperties socialProperties){
        this.accessTokenUrl=accessTokenUrl;
        this.restTemplate=restTemplate;
        this.stringRedisTemplate=stringRedisTemplate;
        this.socialProperties=socialProperties;
    }


    /**
     * 获取令牌
     * @param code 授权码
     * @param state 随机值
     * @return 令牌
     */
    @Override
    public String getAccessToken(String code,String state) {
        MultiValueMap<String, String> params = buildParams(code,state);
        return postForAccessToken(params);
    }

    /**
     * 获取个人用户信息
     * @param accessToken 服务提供商的令牌->com.mugu.blog.social.service.AbstractSocialApI#getAccessToken()
     * @return 服务提供商返回个人用户信息
     */
    @Override
    public UserConnection getUserInfo(String accessToken){
        UserConnection userConnection = doGetUserInfo(accessToken);
        //生成state返回
        String state = UUID.randomUUID().toString().replaceAll("-", "");
        String key= MessageFormat.format("mugu:social:{0}",userConnection.getProviderUserId());
        stringRedisTemplate.opsForValue().set(key,state,5, TimeUnit.MINUTES);
        userConnection.setState(state);
        return userConnection;
    }

    /**
     * 获取个人用户信息
     * @param accessToken 服务提供商的令牌->com.mugu.blog.social.service.AbstractSocialApI#getAccessToken()
     * @return 服务提供商返回个人用户信息
     */
    protected abstract UserConnection doGetUserInfo(String accessToken);

    /**
     * 发出请求令牌
     * @param parameters 请求令牌的参数
     * @return 解析后的令牌
     */
    protected String postForAccessToken(MultiValueMap<String, String> parameters){
        String result = restTemplate.postForObject(accessTokenUrl, parameters, String.class);
        return resolveAccessToken(result);
    }


    /**
     * 构建获取令牌的请求参数
     * @param code 授权码
     * @param state 随机值
     * @return 请求参数
     */
    protected abstract MultiValueMap<String, String> buildParams(String code,String state);

    /**
     * 从令牌请求结果中解析出令牌
     * 由于不同厂商的规范不同，因此设置为抽象方法，由开发者定制
     * @param result 令牌请求结果
     * @return 令牌
     */
    protected abstract String resolveAccessToken(String result);

    public String getAccessTokenUrl() {
        return accessTokenUrl;
    }

    public void setAccessTokenUrl(String accessTokenUrl) {
        this.accessTokenUrl = accessTokenUrl;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public SecuritySocialProperties getSocialProperties() {
        return socialProperties;
    }

    public void setSocialProperties(SecuritySocialProperties socialProperties) {
        this.socialProperties = socialProperties;
    }
}
