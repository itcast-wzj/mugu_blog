package com.mugu.blog.oauth.server.social;

import com.mugu.blog.oauth.server.social.service.SocialUserDetailService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author 公众号：码猿技术专栏
 * @url: www.java-family.cn
 * @description
 */
public class SocialAuthenticationProvider implements AuthenticationProvider {

    private SocialUserDetailService userDetailService;

    public SocialAuthenticationProvider(SocialUserDetailService userDetailService){
        this.userDetailService=userDetailService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SocialAuthenticationToken authenticationToken=(SocialAuthenticationToken)authentication;
        //providerUserId
        String providerUserId = (String) authenticationToken.getPrincipal();
        String providerId = (String) authenticationToken.getProviderId();
        String state= (String) authenticationToken.getState();
        UserDetails userDetails;
        try {
            userDetails = userDetailService.loadByProviderUserId(providerId,providerUserId,state);
        }catch (Exception ex){
            throw new BadCredentialsException(ex.getMessage());
        }
        SocialAuthenticationToken socialAuthenticationToken = new SocialAuthenticationToken(userDetails,providerId,state,userDetails.getAuthorities());
        socialAuthenticationToken.setDetails(authenticationToken.getDetails());
        return socialAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SocialAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
