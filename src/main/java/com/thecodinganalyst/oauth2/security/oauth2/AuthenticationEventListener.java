package com.thecodinganalyst.oauth2.security.oauth2;

import com.thecodinganalyst.oauth2.repository.AppUserRepository;
import com.thecodinganalyst.oauth2.security.user.AppUser;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEventListener {
    UserDetailsService userDetailsService;
    AppUserRepository appUserRepository;

    public AuthenticationEventListener(UserDetailsService userDetailsService, AppUserRepository appUserRepository){
        this.userDetailsService = userDetailsService;
        this.appUserRepository = appUserRepository;
    }

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent successEvent){
        Authentication authentication = successEvent.getAuthentication();

        OAuth2LoginAuthenticationToken oAuth2Token = getOAuth2Token(authentication);
        OAuth2User oAuth2User = getOAuth2User(authentication);

        if(oAuth2Token != null && oAuth2User != null){
            String email = oAuth2User.getAttribute("email");
            if(email == null) return;
            try{
                userDetailsService.loadUserByUsername(email);
            }catch (UsernameNotFoundException exception){
                AppUser appUser = new AppUser(email, null);
                appUserRepository.save(appUser);
            }
        }
    }

    private OAuth2User getOAuth2User(Authentication authentication){
        return authentication.getPrincipal() instanceof OAuth2User user ?
                user :
                null;
    }

    private OAuth2LoginAuthenticationToken getOAuth2Token(Authentication authentication){
        return authentication instanceof OAuth2LoginAuthenticationToken token ?
                token :
                null;
    }


}
