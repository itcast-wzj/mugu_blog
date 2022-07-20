package com.mugu.blog.user.common.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author 公众号：码猿技术专栏
 * @url: www.java-family.cn
 * @description
 */
@Data
public class SysBindReq {

    private Integer id;

    @NotBlank
    private String password;
    @NotBlank
    private String username;

    private String userId;
    @NotBlank
    private String providerId;
    @NotBlank
    private String providerUserId;
    @NotBlank
    private String displayName;

    private String profileUrl;
    @NotBlank
    private String imageUrl;
}
