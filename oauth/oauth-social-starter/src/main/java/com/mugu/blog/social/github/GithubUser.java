package com.mugu.blog.social.github;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubUser {
    //唯一id
    private String id;

    //用户名、账号
    private String login;

    //头像
    private String avatar_url;

    private String name;
}