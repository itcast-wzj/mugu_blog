package com.mugu.blog.common.annotation;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.mugu.blog.core.model.BaseParam;
import com.mugu.blog.core.model.LoginVal;
import com.mugu.blog.core.model.RequestConstant;
import com.mugu.blog.core.model.oauth.OAuthConstant;
import com.mugu.blog.core.utils.RequestContextUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;

@Aspect
@Component
public class AuthInjectionAspect {

    @Around("@annotation(authInjection) &&" +
            "(@annotation(org.springframework.web.bind.annotation.PostMapping)||" +
            "@annotation(org.springframework.web.bind.annotation.GetMapping)||" +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping)||" +
            "@annotation(org.springframework.web.bind.annotation.PutMapping)||" +
            "@annotation(org.springframework.web.bind.annotation.RequestMapping))")
    public Object around(ProceedingJoinPoint joinPoint, AuthInjection authInjection) throws Throwable {
        Object obj = RequestContextUtils.getRequest().getAttribute(RequestConstant.LOGIN_VAL_ATTRIBUTE);
        if (Objects.isNull(obj)){
            return joinPoint.proceed(joinPoint.getArgs());
        }
        LoginVal loginVal=(LoginVal)obj;
        String[] authorities = loginVal.getAuthorities();
        //如果是超管，直接放行，默认获取全部数据
        if (ArrayUtil.contains(authorities, OAuthConstant.ROLE_ROOT_CODE)){
            return joinPoint.proceed(joinPoint.getArgs());
        }
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            MethodSignature signature=(MethodSignature) joinPoint.getSignature();
            Method method = joinPoint.getTarget().getClass().getMethod(signature.getName(), signature.getParameterTypes());
            if (method.getParameterTypes().length==0)
                continue;
            if (!(arg instanceof BaseParam))
                continue;
            BaseParam baseParam=(BaseParam)arg;
            baseParam.setUserId(loginVal.getUserId());
        }
        return joinPoint.proceed(joinPoint.getArgs());
    }
}
