package com.mugu.blog.user.boot.service;

import com.mugu.blog.core.model.ResultMsg;
import com.mugu.blog.user.common.po.SysRole;
import com.mugu.blog.user.common.po.SysUser;
import com.mugu.blog.user.common.po.SysUserConnection;
import com.mugu.blog.user.common.req.SysBindReq;

import java.util.List;

public interface SysUserService {
    SysUser getUserByUsername(String username);

    List<SysRole> getRolesByUserId(Long userId);

    SysUser getByUserId(String userId);

    List<SysUser> listByUserId(List<String> userIds);

    SysUserConnection getByProviderUserId(String providerId,String providerUserId);

    void bind(SysBindReq bindReq);
}
