package com.mugu.blog.article.common.model.req;

import com.mugu.blog.core.model.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ArticleInfoReq extends BaseParam {
    @NotNull
    @ApiModelProperty(value = "文章ID",required = true)
    private String id;
}
