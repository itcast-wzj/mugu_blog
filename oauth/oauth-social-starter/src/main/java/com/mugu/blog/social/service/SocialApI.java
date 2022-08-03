package com.mugu.blog.social.service;

import com.mugu.blog.social.model.UserConnection;

/**
 * @author 公众号：码猿技术专栏
 * @url: www.java-family.cn
 * @description 社交登录的接口定义
 */
public interface SocialApI {

    /**
     * 获取用户信息
     * @param accessToken 令牌
     */
    UserConnection getUserInfo(String accessToken);

    /**
     * 获取令牌
     * @param code 授权码
     * @param state 随机值，防止CSRF攻击
     */
    String getAccessToken(String code,String state);
}
