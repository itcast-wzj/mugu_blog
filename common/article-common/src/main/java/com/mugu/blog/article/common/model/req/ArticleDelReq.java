package com.mugu.blog.article.common.model.req;

import com.mugu.blog.core.model.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ArticleDelReq extends BaseParam {
    @NotBlank
    @ApiModelProperty(value = "文章唯一ID",required = true)
    private String id;
}
