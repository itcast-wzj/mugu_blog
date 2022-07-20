package com.mugu.blog.social.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 公众号：码猿技术专栏
 * @url: www.java-family.cn
 * @description 社交登录的配置类
 */
@Data
@ConfigurationProperties(prefix = "security.social")
public class SecuritySocialProperties {

    private GithubProperties github;

    private QQProperties qq;

    /**
     * Github的配置
     */
    @Data
    public static class GithubProperties{
        /**
         * 是否开启github社交登录
         */
        private boolean enable;

        /**
         * 客户端id
         */
        private String clientId;

        /**
         * 客户端秘钥
         */
        private String clientSecret;

        /**
         * 回调的url
         */
        private String redirectUrl;
    }

    /**
     * QQ的社交配置
     */
    @Data
    public static class QQProperties{

        /**
         * 是否开启QQ社交登录
         */
        private boolean enable;

        /**
         * QQ的appId
         */
        private String appId;

        /**
         * QQ的appKey
         */
        private String appKey;

        /**
         * 回调的URL
         */
        private String redirectUrl;
    }
}
