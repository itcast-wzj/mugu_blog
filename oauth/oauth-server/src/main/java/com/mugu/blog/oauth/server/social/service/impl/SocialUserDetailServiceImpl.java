package com.mugu.blog.oauth.server.social.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.mugu.blog.core.model.ResultMsg;
import com.mugu.blog.core.model.oauth.OAuthConstant;
import com.mugu.blog.oauth.server.model.SecurityUser;
import com.mugu.blog.oauth.server.social.service.SocialUserDetailService;
import com.mugu.blog.user.api.feign.UserFeign;
import com.mugu.blog.user.common.po.SysRole;
import com.mugu.blog.user.common.po.SysUser;
import com.mugu.blog.user.common.po.SysUserConnection;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 公众号：码猿技术专栏
 * @url: www.java-family.cn
 * @description
 */
@Service
public class SocialUserDetailServiceImpl implements SocialUserDetailService {

    @Autowired
    private UserFeign userFeign;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public UserDetails loadByProviderUserId(String providerId,String providerUserId,String state) throws UsernameNotFoundException {
        //随机数校验
        String key= MessageFormat.format("mugu:social:{0}",providerUserId);
        String uuid = stringRedisTemplate.opsForValue().get(key);
        if (Objects.isNull(uuid)||!StrUtil.equals(state,uuid))
            throw new UsernameNotFoundException("未通过校验！");

        ResultMsg<SysUserConnection> userConnectionRes = userFeign.getByProviderId(providerId, providerUserId);
        if (!ResultMsg.isSuccess(userConnectionRes)|| Objects.isNull(userConnectionRes.getData())){
            throw new UsernameNotFoundException("该用户还未绑定！");
        }

        String userId = userConnectionRes.getData().getUserId();

        ResultMsg<SysUser> sysUserRes = userFeign.getByUserId(userId);
        SysUser sysUser = sysUserRes.getData();
        if (!ResultMsg.isSuccess(sysUserRes)||Objects.isNull(sysUser)){
            throw new UsernameNotFoundException("该用户不存在！");
        }
        //获取当前用户的角色
        ResultMsg<List<SysRole>> roleResult = userFeign.getRolesByUserId(sysUser.getId());
        if (!ResultMsg.isSuccess(roleResult)||Objects.isNull(roleResult.getData())){
            throw new UsernameNotFoundException("获取角色失败！");
        }

        List<String> roles = Objects.requireNonNull(roleResult.getData()).stream().map(sysRole -> OAuthConstant.ROLE_PREFIX+sysRole.getCode()).collect(Collectors.toList());
        return SecurityUser.builder()
                .nickname(StrUtil.isBlank(sysUser.getNickname())?userConnectionRes.getData().getDisplayName():sysUser.getNickname())
                .gender(sysUser.getGender())
                .avatar(StrUtil.isBlank(sysUser.getAvatar())?userConnectionRes.getData().getImageUrl():sysUser.getAvatar())
                .mobile(sysUser.getMobile())
                .email(sysUser.getEmail())
                .userId(sysUser.getUserId())
                .username(StrUtil.isBlank(sysUser.getUsername())?userConnectionRes.getData().getDisplayName():sysUser.getUsername())
                .password(sysUser.getPassword())
                //将角色放入authorities中
                .authorities(AuthorityUtils.createAuthorityList(ArrayUtil.toArray(roles,String.class)))
                .build();
    }
}
