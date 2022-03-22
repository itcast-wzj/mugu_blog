package com.mugu.blog.article.intercept;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.mugu.blog.core.constant.KeyConstant;
import com.mugu.blog.core.utils.IpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 统计访问量
 */
@Component
public class ViewIntercept implements HandlerInterceptor {

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        //以文章的list接口作为参考标准
        if (StrUtil.equals(uri,contextPath+"/front/article/list")){
            //单日访问量
            stringRedisTemplate.opsForHyperLogLog().add(KeyConstant.UV+ DateUtil.today(), IpUtils.getIpAddress(request));
            //总计访问量
            stringRedisTemplate.opsForHyperLogLog().add(KeyConstant.UV_TOTAL, IpUtils.getIpAddress(request));
        }
        return true;
    }
}
