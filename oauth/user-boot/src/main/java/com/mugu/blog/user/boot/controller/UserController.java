package com.mugu.blog.user.boot.controller;

import com.mugu.blog.core.model.ResultMsg;
import com.mugu.blog.user.boot.service.SysUserService;
import com.mugu.blog.user.common.po.SysRole;
import com.mugu.blog.user.common.po.SysUser;
import com.mugu.blog.user.common.po.SysUserConnection;
import com.mugu.blog.user.common.req.SysBindReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "OAuth所需的接口")
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    public SysUserService sysUserService;

    @ApiOperation("根据用户名查询详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", defaultValue = "user", value = "用户名", paramType = "query", dataType = "String"),
    })
    @GetMapping("/getByUsername")
    public ResultMsg<SysUser> getByUsername(String username){
        return ResultMsg.resultSuccess(sysUserService.getUserByUsername(username));
    }

    @ApiOperation("根据用户唯一ID查询角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", defaultValue = "user", value = "用户唯一ID", paramType = "query", dataType = "String"),
    })
    @GetMapping("/getRolesByUserId")
    public ResultMsg<List<SysRole>> getRolesByUserId(Long userId){
        return ResultMsg.resultSuccess(sysUserService.getRolesByUserId(userId));
    }

    @ApiOperation("根据用户ID查询用户详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", defaultValue = "user", value = "用户唯一ID", paramType = "query", dataType = "String"),
    })
    @GetMapping("/getByUserId")
    public ResultMsg<SysUser> getByUserId(String userId){
        return ResultMsg.resultSuccess(sysUserService.getByUserId(userId));
    }

    @ApiOperation("根据用户ID批量查询")
    @GetMapping("/listByUserId")
    public ResultMsg<List<SysUser>> listByUserId(@RequestBody List<String> userIds){
        return ResultMsg.resultSuccess(sysUserService.listByUserId(userIds));
    }

    @ApiOperation("根据providerUserId查询用户信息")
    @GetMapping("/getByProviderUserId")
    public ResultMsg<SysUserConnection> getByProviderId(String providerId, String providerUserId){
        return ResultMsg.resultSuccess(sysUserService.getByProviderUserId(providerId,providerUserId));
    }

    @ApiOperation(value = "社交登录绑定/注册用户")
    @PostMapping("/bind")
    public ResultMsg<Void> bind(SysBindReq bindReq){
        sysUserService.bind(bindReq);
        return ResultMsg.resultSuccess();
    }


}
