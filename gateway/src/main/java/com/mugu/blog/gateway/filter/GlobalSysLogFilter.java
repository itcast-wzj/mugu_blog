package com.mugu.blog.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Charsets;
import com.mugu.blog.gateway.model.RequestInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.NettyDataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * @author 公众号：码猿技术专栏
 * @url: www.java-family.cn
 * @description 网关层记录日志
 */
@Component
@Slf4j
public class GlobalSysLogFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setRequestTime(new Date());
        ServerHttpRequest request = exchange.getRequest();
        requestInfo.setUrl(request.getPath().value());
        requestInfo.setMethod(request.getMethodValue());
        HttpHeaders headers = request.getHeaders();

        //获取请求体
        requestInfo.setHeader(JSON.toJSONString(headers.toSingleValueMap()));
        if (MediaType.APPLICATION_JSON.isCompatibleWith(headers.getContentType())
                ||MediaType.APPLICATION_FORM_URLENCODED.isCompatibleWith(headers.getContentType())){
            String body = resolveBody(exchange);
            requestInfo.setBody(body);
        }else if (MediaType.MULTIPART_FORM_DATA.isCompatibleWith(headers.getContentType())){
            requestInfo.setBody(null);
        }else {
            requestInfo.setBody(JSON.toJSONString(request.getQueryParams().toSingleValueMap()));
        }
        //写入日志
        writeLog(requestInfo);
        return chain.filter(exchange);
    }

    private String resolveBody(ServerWebExchange exchange){
        Object cachedBody = exchange.getAttribute(ServerWebExchangeUtils.CACHED_REQUEST_BODY_ATTR);
        if (cachedBody!=null){
            NettyDataBuffer buffer = (NettyDataBuffer) cachedBody;
            return buffer.toString(Charsets.UTF_8);
        }
        return null;
    }

    private void writeLog(RequestInfo requestInfo){
//        log.info("收到一条请求，请求参数：{}",JSON.toJSONString(requestInfo));
        //TODO 日志持久化
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
