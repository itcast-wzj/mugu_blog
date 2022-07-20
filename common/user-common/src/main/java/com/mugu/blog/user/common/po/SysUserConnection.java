package com.mugu.blog.user.common.po;

import lombok.Data;

/**
 * @author 公众号：码猿技术专栏
 * @url: www.java-family.cn
 * @description
 */
@Data
public class SysUserConnection {
    private String userId;

    private String providerId;

    private String providerUserId;

    private Integer rank;

    private String displayName;

    private String profileUrl;

    private String imageUrl;

    private String accessToken;

    private String secret;

    private String refreshToken;

    private Long expireTime;
}
