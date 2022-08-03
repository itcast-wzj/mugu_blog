package com.mugu.blog.social.controller;

import com.mugu.blog.core.model.ResultMsg;
import com.mugu.blog.social.model.SecuritySocialProperties;
import com.mugu.blog.social.model.SocialConstant;
import com.mugu.blog.social.model.SocialVo;
import com.mugu.blog.social.model.UserConnection;
import com.mugu.blog.social.service.SocialApI;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author 公众号：码猿技术专栏
 * @url: www.java-family.cn
 * @description 社交登录相关的接口
 */
@RestController
@RequestMapping(value = "/social")
public class SocialAuthController {

    /**
     * 社交登录的不同实现类，根据providerId去筛选
     */
    @Autowired
    private Map<String,SocialApI> socialApIMap;

    @Autowired
    private SecuritySocialProperties socialProperties;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 社交登录获取请求授权码的URL
     * @param providerId 服务商id
     */
    @PostMapping("/{providerId}/getAuthorizeUrl")
    public ResultMsg<SocialVo> getAuthorizeUrl(@PathVariable String providerId){
        String authorizeUrl=null;
        //生成一个随机的uuid，防止CSRF攻击
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        switch (providerId){
            case "github":
                authorizeUrl= MessageFormat.format(SocialConstant.GITHUB_AUTHORIZE_URL,socialProperties.getGithub().getClientId(),socialProperties.getGithub().getRedirectUrl(),"user",uuid);
                break;
            case "qq":
                authorizeUrl= MessageFormat.format(SocialConstant.QQ_AUTHORIZE_URL,socialProperties.getQq().getAppId(),socialProperties.getQq().getRedirectUrl(),uuid);
                break;
        }
        //将uuid放入redis
        stringRedisTemplate.opsForValue().set(MessageFormat.format("mugu:social:state:{0}",uuid),"",5, TimeUnit.MINUTES);

        return ResultMsg.resultSuccess(SocialVo.builder().authorizeUrl(authorizeUrl).state(uuid).build());
    }



    /**
     * 根据授权码获取令牌
     * @param state 随机数
     * @param code 授权码
     * @param providerId 厂商
     */
    @SneakyThrows
    @PostMapping("/{providerId}/accessToken")
    public ResultMsg<String> getAccessToken(String code, String state,@PathVariable String providerId){
        //校验state
        String key=MessageFormat.format("mugu:social:state:{0}",state);
        String value = stringRedisTemplate.opsForValue().get(key);
        if (Objects.isNull(value)){
            return ResultMsg.resultFail();
        }
        return ResultMsg.resultSuccess(socialApIMap.get(MessageFormat.format("{0}SocialApI",providerId)).getAccessToken(code,state));
    }

    /**
     * 获取个人身份信息
     * @param accessToken 令牌
     * @param providerId 厂商
     * @return
     */
    @PostMapping("/{providerId}/getUserInfo")
    @SneakyThrows
    public ResultMsg<UserConnection> getUserInfo(String accessToken, @PathVariable String providerId){
        return ResultMsg.resultSuccess(socialApIMap.get(MessageFormat.format("{0}SocialApI",providerId)).getUserInfo(accessToken));
    }
}
