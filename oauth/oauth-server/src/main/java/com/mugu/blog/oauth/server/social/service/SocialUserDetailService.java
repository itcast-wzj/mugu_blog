package com.mugu.blog.oauth.server.social.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author 公众号：码猿技术专栏
 * @url: www.java-family.cn
 * @description 根据providerUserId查询指定的信息
 */
public interface SocialUserDetailService  {
    /**
     * 从数据库查询相关用户信息
     * @param providerId 服务提供商的id
     * @param providerUserId 服务提供商的用户身份唯一id
     * @param state 随机数
     */
    UserDetails loadByProviderUserId(String providerId,String providerUserId,String state)throws UsernameNotFoundException;
}
