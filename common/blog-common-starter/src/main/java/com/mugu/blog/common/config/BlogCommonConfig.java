package com.mugu.blog.common.config;

import com.mugu.blog.common.annotation.AuthInjectionAspect;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.mugu.blog.common.filter","com.mugu.blog.common.exception","com.mugu.blog.common.intercept"},
    basePackageClasses = {AuthInjectionAspect.class}
)
public class BlogCommonConfig {
}
