package com.mugu.blog.user.boot.dao;

import com.mugu.blog.user.common.po.SysRole;
import com.mugu.blog.user.common.po.SysUser;
import com.mugu.blog.user.common.po.SysUserConnection;
import com.mugu.blog.user.common.req.SysBindReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserMapper {
    /**
     * 根据用户名查询用户详细信息
     */
    SysUser selectByUserName(String username);

    List<SysRole> selectRolesByUserId(Long userId);

    SysUser selectByUserId(String userId);

    List<SysUser> listByUserId(List<String> list);

    SysUserConnection selectByProviderUserId(@Param("providerId") String providerId, @Param("providerUserId") String providerUserId);

    int addUserConnection(SysBindReq bindReq);

    int addSysUser(SysBindReq bindReq);

    int addSysRole(@Param("userId") Integer userId,@Param("roleId") Integer roleId);

}
