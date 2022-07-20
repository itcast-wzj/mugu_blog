package com.mugu.blog.social.qq;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.system.UserInfo;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mugu.blog.social.model.SecuritySocialProperties;
import com.mugu.blog.social.model.SocialConstant;
import com.mugu.blog.social.model.UserConnection;
import com.mugu.blog.social.service.AbstractSocialApI;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

/**
 * @author 公众号：码猿技术专栏
 * @url: www.java-family.cn
 * @description QQ的社交登录实现接口
 */
public class QQSocialApI extends AbstractSocialApI {

    private String userInfoUrl;

    private String openIdUrl;

    /**
     * 构造方法
     * @param openIdUrl 获取openId的URL
     * @param userInfoUrl 获取用户信息的URL
     * @param accessTokenUrl 获取令牌的URL
     * @param restTemplate RestTemplate
     * @param socialProperties 配置类
     */
    public QQSocialApI(String openIdUrl, String userInfoUrl, String accessTokenUrl, RestTemplate restTemplate, StringRedisTemplate stringRedisTemplate,SecuritySocialProperties socialProperties) {
        super(accessTokenUrl, restTemplate,stringRedisTemplate, socialProperties);
        this.userInfoUrl=userInfoUrl;
        this.openIdUrl=openIdUrl;
    }

    @Override
    public UserConnection doGetUserInfo(String accessToken) {
        OpenResponse openResponse = getOpenId(accessToken);
        if (Objects.isNull(openResponse)||StrUtil.isNotBlank(openResponse.getError()))
            return null;
        //获取openId
        String openId = openResponse.getOpenId();
        //构造获取个人信息的URL
        String url=MessageFormat.format(userInfoUrl,accessToken,getSocialProperties().getQq().getAppId(),openId);
        String result = getRestTemplate().getForObject(url, String.class);
        QQUserInfo userInfo = JSON.parseObject(result, QQUserInfo.class);
        if (!StrUtil.equals(userInfo.getRet(),"0"))
            return null;
        return convertToUserConnection(userInfo,openId);
    }

    private UserConnection convertToUserConnection(QQUserInfo userInfo,String openId){
        return UserConnection.builder()
                .providerId("qq")
                .providerUserId(openId)
                .displayName(userInfo.getNickname())
                .imageUrl(userInfo.getFigureurl_qq_1())
                .build();
    }

    /**
     * 获取openId
     * @param accessToken 令牌
     */
    protected OpenResponse getOpenId(String accessToken){
        String url= MessageFormat.format(openIdUrl,accessToken);
        String result = getRestTemplate().getForObject(url, String.class);
        return JSON.parseObject(StrUtil.subBetween(result, "(", ")"),OpenResponse.class);
    }


    @Override
    protected MultiValueMap<String, String> buildParams(String code, String state) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id",getSocialProperties().getQq().getAppId());
        params.add("client_secret",getSocialProperties().getQq().getAppKey());
        params.add("redirect_uri",getSocialProperties().getQq().getRedirectUrl());
        params.add("code",code);
        params.add("state",state);
        params.add("grant_type","authorization_code");
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

    public String getOpenIdUrl() {
        return openIdUrl;
    }

    public void setOpenIdUrl(String openIdUrl) {
        this.openIdUrl = openIdUrl;
    }
}
