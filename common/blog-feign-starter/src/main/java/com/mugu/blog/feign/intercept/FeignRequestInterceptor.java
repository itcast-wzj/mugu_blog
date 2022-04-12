package com.mugu.blog.feign.intercept;

import cn.hutool.core.util.StrUtil;
import com.mugu.blog.core.constant.GrayConstant;
import com.mugu.blog.core.model.oauth.OAuthConstant;
import com.mugu.blog.feign.utils.RequestContextUtils;
import com.mugu.blog.gray.utils.GrayRequestContextHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 解决feign中的令牌中继问题
 */
@Component
@Slf4j
public class FeignRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        HttpServletRequest httpServletRequest = RequestContextUtils.getRequest();
        Map<String, String> headers = getHeaders(httpServletRequest);
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            template.header(entry.getKey(), entry.getValue());
        }
    }

    /**
     * 获取原请求头
     */
    private Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> map = new LinkedHashMap<>();
        Enumeration<String> enumeration = request.getHeaderNames();
        if (enumeration != null) {
            while (enumeration.hasMoreElements()) {
                String key = enumeration.nextElement();
                String value = request.getHeader(key);
                //需要传递的两个请求头==》 1.令牌信息  2. 灰度发布的请求头
                if (StrUtil.equals(OAuthConstant.TOKEN_NAME,key)){
                    map.put(key, value);
                }

                //将灰度标记的请求头透传给下个服务
                if (StrUtil.equals(GrayConstant.GRAY_HEADER,key)&&Boolean.TRUE.toString().equals(value)){
                    //保存灰度发布的标记
                    GrayRequestContextHolder.setGrayTag(true);
                    map.put(key, value);
                }
            }
        }
        return map;
    }
}