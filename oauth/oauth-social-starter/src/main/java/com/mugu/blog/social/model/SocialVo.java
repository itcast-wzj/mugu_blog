package com.mugu.blog.social.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 公众号：码猿技术专栏
 * @url: www.java-family.cn
 * @description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocialVo {
    /**
     * 授权的url
     */
    private String authorizeUrl;

    /**
     * state随机值
     */
    private String state;
}
