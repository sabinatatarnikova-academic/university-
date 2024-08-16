package com.foxminded.university.controller;

import com.foxminded.university.config.security.CustomAuthenticationSuccessHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CustomAuthenticationSuccessHandlerTest {

    private CustomAuthenticationSuccessHandler handler;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        handler = new CustomAuthenticationSuccessHandler();
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        authentication = Mockito.mock(Authentication.class);
    }

    @Test
    void testOnAuthenticationSuccessWithAdminRole() throws IOException {
        Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
        when(authentication.getAuthorities()).thenReturn((Collection) authorities);
        handler.onAuthenticationSuccess(request, response, authentication);

        verify(response).sendRedirect("/admin");
    }

    @Test
    void testOnAuthenticationSuccessWithTeacherRole() throws IOException {
        Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_TEACHER"));
        when(authentication.getAuthorities()).thenReturn((Collection) authorities);
        handler.onAuthenticationSuccess(request, response, authentication);

        verify(response).sendRedirect("/teacher");
    }

    @Test
    void testOnAuthenticationSuccessWithStudentRole() throws IOException {
        Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_STUDENT"));
        when(authentication.getAuthorities()).thenReturn((Collection) authorities);
        handler.onAuthenticationSuccess(request, response, authentication);

        verify(response).sendRedirect("/student");
    }

    @Test
    void testOnAuthenticationSuccessWithNoRole() throws IOException {
        when(authentication.getAuthorities()).thenReturn(Collections.emptyList());
        handler.onAuthenticationSuccess(request, response, authentication);

        verify(response).sendRedirect("/");
    }
}
