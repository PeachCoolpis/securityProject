package io.securityproject.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.securityproject.users.dto.AccountDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component("restAuthenticationSuccessHandler")
public class RestAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        
        ObjectMapper mapper = new ObjectMapper();
        AccountDto accountDto = (AccountDto) authentication.getPrincipal();
        response.setStatus(HttpServletResponse.SC_OK);  // http 200코드
        response.setContentType(MediaType.APPLICATION_JSON_VALUE); // 응답 contentType : applcation/json
        accountDto.setPassword(null); // 비밀번호는 null값으로 바꿔준다
        mapper.writeValue(response.getWriter(),accountDto);
        
        clearAuthenticationAttributes(request);
    }
    
    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 세션을 만들지 않는다.
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION); // 마지막 예외 지우기
    }
}
