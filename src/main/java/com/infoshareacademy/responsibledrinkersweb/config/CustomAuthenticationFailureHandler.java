package com.infoshareacademy.responsibledrinkersweb.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Value("${message.login.user.not_active}")
    private String failureText;
    @Value("${message.login.user.invalid}")
    private String userInvalidText;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (exception.getMessage().equals(failureText)) {
            response.sendRedirect("/login?error=" + failureText);
        } else {
            response.sendRedirect("/login?error=" + userInvalidText);
        }
    }
}
