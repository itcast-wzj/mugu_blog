package com.mugu.blog.social.model;

/**
 * @author 公众号：码猿技术专栏
 * @url: www.java-family.cn
 * @description
 */
public class SocialConstant {
    /**
     * Github：获取授权码的URL
     */
    public final static String GITHUB_AUTHORIZE_URL="https://github.com/login/oauth/authorize?client_id={0}&redirect_uri={1}&scope={2}&state={3}";
    /**
     * Github：获取令牌的URL
     */
    public final static String GITHUB_ACCESS_TOKEN_URL="https://github.com/login/oauth/access_token";

    /**
     * Github：获取个人信息的URL
     */
    public final static String GITHUB_USER_INFO_URL="https://api.github.com/user";


    /**
     * QQ：获取授权码的URL
     * {0}：App ID
     * {1}：回调url
     * {2}：随机值，防止csrf攻击
     */
    public final static String QQ_AUTHORIZE_URL="https://graph.qq.com/oauth2.0/show?which=Login&display=pc&response_type=code&client_id={0}&redirect_uri={1}&state={2}";

    /**
     * QQ：获取用户信息的URL
     * {0}:令牌
     * {1}:appId
     * {2}:openId
     */
    public final static String QQ_USER_INFO_URL="https://graph.qq.com/user/get_user_info?access_token={0}&oauth_consumer_key={1}&openid={2}";

    /**
     * QQ：获取openId的URL
     * {0}：令牌
     */
    public final static String QQ_OPENID_URL="https://graph.qq.com/oauth2.0/me?access_token={0}";

    /**
     * QQ：获取令牌的URL
     */
    public final static String ACCESS_TOKEN_URL="https://graph.qq.com/oauth2.0/token";
}
