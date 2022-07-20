package com.mugu.blog.social.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 公众号：码猿技术专栏
 * @url: www.java-family.cn
 * @description 抽象实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserConnection {
    //业务系统的唯一userId
    private String userId;

    //服务提供商
    private String providerId;

    //服务提供商的唯一id
    private String providerUserId;

    private Integer rank;

    //昵称
    private String displayName;

    //用户主页
    private String profileUrl;

    //头像
    private String imageUrl;

    //令牌
    private String accessToken;

    //秘钥
    private String secret;

    //刷新令牌
    private String refreshToken;

    //过期时间
    private Long expireTime;

    /**
     * 随机数，保证providerUserId+state验证
     */
    private String state;
}
