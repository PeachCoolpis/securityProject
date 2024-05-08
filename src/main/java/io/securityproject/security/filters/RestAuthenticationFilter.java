package io.securityproject.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.securityproject.security.token.RestAuthenticationToken;
import io.securityproject.users.dto.AccountDto;
import io.securityproject.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.bridge.MessageWriter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import java.io.IOException;

public class RestAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public RestAuthenticationFilter() {
        super(new AntPathRequestMatcher("/api/login","POST"));
    }
    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        
        if (!HttpMethod.POST.name().equals(request.getMethod()) || !WebUtil.isAjax(request)) {
            throw new IllegalArgumentException("Authentication method not supported");
        }
        AccountDto accountDto = objectMapper.readValue(request.getReader(), AccountDto.class);
        
        if (!StringUtils.hasText(accountDto.getUsername()) || !StringUtils.hasText(accountDto.getPassword())) {
            throw new AuthenticationServiceException("Username or Password not provided");
        }
        
        
        RestAuthenticationToken restAuthenticationToken = new RestAuthenticationToken(accountDto.getUsername(), accountDto.getPassword());
        
        return this.getAuthenticationManager().authenticate(restAuthenticationToken);
    }
}
