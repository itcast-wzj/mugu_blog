package com.mugu.blog.social.qq;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author 公众号：码猿技术专栏
 * @url: www.java-family.cn
 * @description QQ获取openId的返回结果
 */
@Data
public class OpenResponse {
    @JSONField(name = "client_id")
    private String clientId;

    @JSONField(name = "openid")
    private String openId;

    @JSONField(name = "error")
    private String error;

    @JSONField(name = "error_description")
    private String errorDescription;
}
