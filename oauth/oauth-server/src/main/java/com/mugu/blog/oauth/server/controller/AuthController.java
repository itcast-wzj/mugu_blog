package com.mugu.blog.oauth.server.controller;

import com.mugu.blog.core.model.LoginVal;
import com.mugu.blog.core.model.ResultMsg;
import com.mugu.blog.core.model.oauth.OAuthConstant;
import com.mugu.blog.core.utils.OauthUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@Api(value = "OAuth接口")
@RequestMapping("/oauth")
@Controller
@Slf4j
public class AuthController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @ApiOperation(value = "注销")
    @PostMapping("/logout")
    public ResultMsg<Void> logout(){
        LoginVal loginVal = OauthUtils.getCurrentUser();
        //这个jti放入redis中，并且过期时间设置为token的过期时间
        stringRedisTemplate.opsForValue().set(OAuthConstant.JTI_KEY_PREFIX + OauthUtils.getCurrentUser().getJti(),"",loginVal.getExpireIn(), TimeUnit.SECONDS);
        return ResultMsg.resultSuccess();
    }

    @ApiOperation(value = "表单登录跳转页面")
    @GetMapping("/login")
    public String loginPage(Model model){
        //返回跳转页面
        return "oauth-login";
    }

    @ApiOperation(value = "处理授权异常的跳转页面")
    @GetMapping("/error")
    public String error(Model model){
        return "oauth-error";
    }
}
