package com.mugu.blog.gray.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 将灰度发布的标记存储在ThreadLocal中实现每个请求隔离
 */
public class RibbonRequestContextHolder {
    public static ThreadLocal<Map<String,String>> currentContext=new ThreadLocal<>();

    public static Map<String,String> getCurrentContext(){
        Map<String, String> map = currentContext.get();
        if (Objects.isNull(map)){
            map=new HashMap<>();
        }
        return map;
    }

    public static void setCurrentContext(Map<String,String> map){
        currentContext.set(map);
    }

    public static void put(String key,String value){
        HashMap<String, String> map = new HashMap<>();
        map.put(key,value);
        currentContext.set(map);
    }


    public static void clearContext(){
        currentContext.remove();
    }

}

