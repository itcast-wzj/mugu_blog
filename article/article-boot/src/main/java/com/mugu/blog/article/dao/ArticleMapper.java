package com.mugu.blog.article.dao;

import com.mugu.blog.article.common.model.po.Article;
import com.mugu.blog.article.common.model.po.ArticleIp;
import com.mugu.blog.article.common.model.req.ArticleDelReq;
import com.mugu.blog.article.common.model.req.ArticleInfoReq;
import com.mugu.blog.article.common.model.req.ArticleListReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleMapper {

    /**
    * 新增
    * @author zhengkai.blog.csdn.net
    * @date 2022/01/15
    **/
    int insert(Article article);

    /**
    * 刪除
    * @author zhengkai.blog.csdn.net
    * @date 2022/01/15
    **/
    int delete(int id);

    /**
    * 更新
    * @author zhengkai.blog.csdn.net
    * @date 2022/01/15
    **/
    int update(Article article);

    /**
    * 查询 根据主键 id 查询
    * @date 2022/01/15
    **/
    Article load(ArticleInfoReq param);

    /**
    * 查询 分页查询
    * @author zhengkai.blog.csdn.net
    * @date 2022/01/15
    **/
    List<Article> pageList(ArticleListReq param);

    /**
    * 查询 分页查询 count
    * @author zhengkai.blog.csdn.net
    * @date 2022/01/15
    **/
    int pageListCount(int offset,int pagesize);

    int delById(ArticleDelReq params);

    int insertArticleIp(ArticleIp articleIp);

    long total();

}