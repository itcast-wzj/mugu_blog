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
     */
    UserConnection getUserInfo(String accessToken);

    /**
     * 获取令牌
     */
    String getAccessToken(String code,String state);
}
