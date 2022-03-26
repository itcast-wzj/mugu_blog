package com.mugu.blog.gray.rule;

import cn.hutool.core.util.StrUtil;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.google.common.base.Optional;
import com.mugu.blog.core.constant.GrayConstant;
import com.mugu.blog.gray.utils.RibbonRequestContextHolder;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ZoneAvoidanceRule;

import java.util.ArrayList;
import java.util.List;

/**
 * 灰度发布的规则
 * 逻辑：从请求头中获取标记的
 */
public class GrayRule extends ZoneAvoidanceRule {

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {
    }

    @Override
    public Server choose(Object key) {
        try {
            String grayTag = RibbonRequestContextHolder.getCurrentContext().get(GrayConstant.GRAY_HEADER);
            //获取所有可用服务
            List<Server> serverList = this.getLoadBalancer().getReachableServers();
            //灰度发布的服务
            List<Server> grayServerList = new ArrayList<>();
            //正常的服务
            List<Server> normalServerList = new ArrayList<>();
            for(Server server : serverList) {
                NacosServer nacosServer = (NacosServer) server;
                //从nacos中获取元素剧进行匹配
                if(nacosServer.getMetadata().containsKey(GrayConstant.GRAY_HEADER)
                        && nacosServer.getMetadata().get(GrayConstant.GRAY_HEADER).equals(GrayConstant.GRAY_VALUE)) {
                    grayServerList.add(server);
                } else {
                    normalServerList.add(server);
                }
            }
            //如果被标记为灰度发布，则调用灰度发布的服务
            if(StrUtil.isNotBlank(grayTag)&&StrUtil.equals(GrayConstant.GRAY_VALUE,grayTag)) {
                return originChoose(grayServerList,key);
            } else {
                return originChoose(normalServerList,key);
            }
        } finally {
            //清除灰度标记
            RibbonRequestContextHolder.clearContext();
        }
    }

    private Server originChoose(List<Server> noMetaServerList, Object key) {
        Optional<Server> server = getPredicate().chooseRoundRobinAfterFiltering(noMetaServerList, key);
        if (server.isPresent()) {
            return server.get();
        } else {
            return null;
        }
    }
}
