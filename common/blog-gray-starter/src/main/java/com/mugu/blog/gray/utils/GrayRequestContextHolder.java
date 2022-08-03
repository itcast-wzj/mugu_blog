package com.mugu.blog.gray.utils;

import java.util.Objects;

/**
 * 将灰度发布的标记存储在ThreadLocal中实现每个请求隔离
 */
public class GrayRequestContextHolder {

    //保存灰度标记
    public static ThreadLocal<Boolean> grayTag=new ThreadLocal<>();

    public static ThreadLocal<Boolean> getGrayTag(){
        Boolean tag = grayTag.get();
        if (Objects.isNull(tag))
            grayTag.set(false);
        return grayTag;
    }

    public static void setGrayTag(boolean tag){
        grayTag.set(tag);
    }

    public static void remove(){
        grayTag.remove();
    }
}

