package com.mugu.blog.social.github;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mugu.blog.social.model.SecuritySocialProperties;
import com.mugu.blog.social.model.UserConnection;
import com.mugu.blog.social.service.AbstractSocialApI;
import lombok.SneakyThrows;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author 公众号：码猿技术专栏
 * @url: www.java-family.cn
 * @description Github的实现
 */
public class GithubSocialApI extends AbstractSocialApI {

    private String userInfoUrl;


    public GithubSocialApI(String accessTokenUrl, String userInfoUrl, RestTemplate restTemplate, StringRedisTemplate stringRedisTemplate,SecuritySocialProperties socialProperties) {
        super(accessTokenUrl, restTemplate, stringRedisTemplate,socialProperties);
        this.userInfoUrl=userInfoUrl;
    }

    @SneakyThrows
    @Override
    public UserConnection doGetUserInfo(String accessToken) {
        //获取个人信息
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "token "+accessToken);
        ResponseEntity<String> userInfoResponse = getRestTemplate().exchange(
                userInfoUrl,
                HttpMethod.GET,
                new HttpEntity<String>(headers),
                String.class);
        GithubUser githubUser = new ObjectMapper().readValue(userInfoResponse.getBody(), GithubUser.class);
        return convertToUserConnection(githubUser);
    }

    private UserConnection convertToUserConnection(GithubUser user){
        return UserConnection.builder()
                .providerId("github")
                .providerUserId(user.getId())
                .displayName(user.getName())
                .imageUrl(user.getAvatar_url())
                .build();
    }

    @Override
    protected MultiValueMap<String, String> buildParams(String code,String state) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id",getSocialProperties().getGithub().getClientId());
        params.add("client_secret",getSocialProperties().getGithub().getClientSecret());
        params.add("redirect_uri",getSocialProperties().getGithub().getRedirectUrl());
        params.add("code",code);
        params.add("state",state);
        return params;
    }

    @Override
    protected String resolveAccessToken(String result) {
        if (!StrUtil.contains(result,"access_token"))
            return null;
        //解析出令牌
        List<String> split = StrUtil.split(result, "&");
        for (String str : split) {
            if (StrUtil.contains(str,"access_token")){
                return str.split("=")[1];
            }
        }
        return null;
    }

    public String getUserInfoUrl() {
        return userInfoUrl;
    }

    public void setUserInfoUrl(String userInfoUrl) {
        this.userInfoUrl = userInfoUrl;
    }
}
