package com.mugu.blog.user.boot.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import com.mugu.blog.core.model.ResultMsg;
import com.mugu.blog.core.utils.SnowflakeUtil;
import com.mugu.blog.user.boot.dao.SysUserMapper;
import com.mugu.blog.user.boot.service.SysUserService;
import com.mugu.blog.user.common.po.SysRole;
import com.mugu.blog.user.common.po.SysUser;
import com.mugu.blog.user.common.po.SysUserConnection;
import com.mugu.blog.user.common.req.SysBindReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser getUserByUsername(String username) {
        return sysUserMapper.selectByUserName(username);
    }

    @Override
    public List<SysRole> getRolesByUserId(Long userId) {
        return sysUserMapper.selectRolesByUserId(userId);
    }

    @Override
    public SysUser getByUserId(String userId) {
        return sysUserMapper.selectByUserId(userId);
    }

    @Override
    public List<SysUser> listByUserId(List<String> userIds) {
        if (CollectionUtil.isEmpty(userIds))
            return null;
        return sysUserMapper.listByUserId(userIds);
    }

    @Override
    public SysUserConnection getByProviderUserId(String providerId,String providerUserId) {
        return sysUserMapper.selectByProviderUserId(providerId,providerUserId);
    }

    @Transactional
    @Override
    public void bind(SysBindReq bindReq) {
        //1. 向sys_userconnection添加一条记录
        SysUserConnection userConnection = sysUserMapper.selectByProviderUserId(bindReq.getProviderId(), bindReq.getProviderUserId());
        Assert.isNull(userConnection);

        long userId = SnowflakeUtil.nextId();
        bindReq.setUserId(String.valueOf(userId));
        int i = sysUserMapper.addUserConnection(bindReq);
        Assert.isTrue(i==1);

        //添加用户、权限、角色
        SysUser sysUser = sysUserMapper.selectByUserName(bindReq.getUsername());
        Assert.isNull(sysUser);
        int j = sysUserMapper.addSysUser(bindReq);
        Assert.isTrue(j==1);
        //TODO 暂时赋予管理员的权限
        int k = sysUserMapper.addSysRole(bindReq.getId(), 2);
        Assert.isTrue(k==1);
    }
}

