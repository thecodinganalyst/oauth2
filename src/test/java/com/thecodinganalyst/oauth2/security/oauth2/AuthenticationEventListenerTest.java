package com.thecodinganalyst.oauth2.security.oauth2;

import com.thecodinganalyst.oauth2.repository.AppUserRepository;
import com.thecodinganalyst.oauth2.security.user.AppUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationEventListenerTest {

    @InjectMocks
    AuthenticationEventListener authenticationEventListener;

    @Mock
    AuthenticationSuccessEvent successEvent;

    @Mock
    UserDetailsService userDetailsService;

    @Mock
    OAuth2LoginAuthenticationToken authentication;

    @Mock
    OAuth2User oAuth2User;

    @Mock
    UserDetails userDetails;

    @Mock
    AppUserRepository appUserRepository;

    @Test
    void givenUserDoesNotExist_whenLoginWithOAuth2_thenAddUser() {

        when(successEvent.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(oAuth2User);
        when(oAuth2User.getAttribute("email")).thenReturn("user@example.com");
        when(userDetailsService.loadUserByUsername(anyString())).thenThrow(new UsernameNotFoundException(""));

        authenticationEventListener.onSuccess(successEvent);

        verify(userDetailsService).loadUserByUsername("user@example.com");
        verify(appUserRepository).save(any(AppUser.class));
    }

    @Test
    void givenUserDoExist_whenLoginWithOAuth2_thenUserNotAdded() {

        when(successEvent.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(oAuth2User);
        when(oAuth2User.getAttribute("email")).thenReturn("user@example.com");
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(userDetails);

        authenticationEventListener.onSuccess(successEvent);

        verify(userDetailsService).loadUserByUsername("user@example.com");
        verify(appUserRepository, never()).save(any(AppUser.class));
    }
}