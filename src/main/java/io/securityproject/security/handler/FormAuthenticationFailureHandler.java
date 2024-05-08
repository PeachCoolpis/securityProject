package io.securityproject.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component(value = "formAuthenticationFailureHandler")
public class FormAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        
        String errorMessage = "아이디 또는 비밀번호 일치하지 않음";
        
        if (exception instanceof BadCredentialsException) {
            errorMessage = "BadCredentials";
        } else if (exception instanceof UsernameNotFoundException) {
            errorMessage = "Not Found User";
        } else if (exception instanceof CredentialsExpiredException) {
            errorMessage = "CredentialsExpired";
        }
        setDefaultFailureUrl("/login?error=true&exception="+ errorMessage);
        super.onAuthenticationFailure(request, response, exception);
    }
}
