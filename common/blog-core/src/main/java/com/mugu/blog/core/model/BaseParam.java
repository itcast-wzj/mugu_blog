package com.mugu.blog.core.model;

import cn.hutool.core.util.StrUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class BaseParam {

    private Integer pageNum;

    private Integer pageSize;

    private String keyword;

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getKeyword() {
        if (StrUtil.isNotBlank(keyword)){
            try {
                return URLDecoder.decode(keyword, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
