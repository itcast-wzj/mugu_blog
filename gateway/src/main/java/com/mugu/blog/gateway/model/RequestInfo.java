package com.mugu.blog.gateway.model;

import lombok.Data;

import java.util.Date;

/**
 * @author 公众号：码猿技术专栏
 * @url: www.java-family.cn
 * @description 请求参数信息
 */
@Data
public class RequestInfo {
    private String url;

    private String method;

    private String body;

    private String header;

    private Date requestTime;
}
