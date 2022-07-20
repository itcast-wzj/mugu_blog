package com.mugu.blog.oauth.server.social.config;

import com.mugu.blog.oauth.server.social.SocialAuthenticationProvider;
import com.mugu.blog.oauth.server.social.service.SocialUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;

/**
 * @author 公众号：码猿技术专栏
 * @url: www.java-family.cn
 * @description
 */
@Configuration
public class SocialSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private SocialUserDetailService socialUserDetailService;

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        SocialAuthenticationProvider authenticationProvider = new SocialAuthenticationProvider(socialUserDetailService);
        builder.authenticationProvider(authenticationProvider);
    }
}
