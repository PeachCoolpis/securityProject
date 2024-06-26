package io.securityproject.security.provider;

import io.securityproject.security.details.FormAuthenticationDetails;
import io.securityproject.users.dto.AccountContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component("authenticationProvider")
@RequiredArgsConstructor
public class FormAuthenticationProvider implements AuthenticationProvider {
    
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        AccountContext accountContext = (AccountContext) userDetailsService.loadUserByUsername(username); // null 이면 userDetailsService 에서 exception 던짐
        
        if (!passwordEncoder.matches(password, accountContext.getPassword())) {
            throw new BadCredentialsException("비밀번호 불일치");
        }
        
        FormAuthenticationDetails formAuthenticationDetails = (FormAuthenticationDetails) authentication.getDetails();
        // authentication.getDetails 안에  WebAuthenticationDetails 가 저장되어있음 이건 따로 복습이 필요함
        String secretKey = formAuthenticationDetails.getSecretKey();
        if (!"secret".equals(secretKey)) {
            throw new BadCredentialsException("시크릿키 안맞음");
        }
        
        return new UsernamePasswordAuthenticationToken(accountContext.getAccountDto(),null,accountContext.getAuthorities());
    }
    
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
