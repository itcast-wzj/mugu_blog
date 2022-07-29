package com.mugu.blog.article.service;

import com.mugu.blog.article.common.model.req.ArticleAddReq;
import com.mugu.blog.article.common.model.req.ArticleDelReq;
import com.mugu.blog.article.common.model.req.ArticleInfoReq;
import com.mugu.blog.article.common.model.req.ArticleListReq;
import com.mugu.blog.article.common.model.vo.ArticleVo;
import com.mugu.blog.core.model.BaseParam;
import com.mugu.blog.core.model.PageData;

import java.util.List;

public interface ArticleService {
    void add(ArticleAddReq article);

    PageData<ArticleVo> list(ArticleListReq req);

    int delById(ArticleDelReq params);

    ArticleVo getById(ArticleInfoReq param);

    PageData<ArticleVo> search(BaseParam req);

    Long total();
}
