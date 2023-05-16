package com.thecodinganalyst.oauth2.security;

import com.thecodinganalyst.oauth2.repository.AppUserRepository;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthorizationCodeAuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEventListener {

    AppUserRepository appUserRepository;

    public AuthenticationEventListener(AppUserRepository appUserRepository){
        this.appUserRepository = appUserRepository;
    }

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent successEvent){
        Authentication authentication = successEvent.getAuthentication();

        if(authentication instanceof OAuth2AuthenticationToken oAuth2AuthenticationToken){

        } else if (authentication instanceof OAuth2AuthorizationCodeAuthenticationToken oAuth2AuthorizationCodeAuthenticationToken) {

        } else if (authentication instanceof OAuth2LoginAuthenticationToken oAuth2LoginAuthenticationToken){

        }
    }
}
