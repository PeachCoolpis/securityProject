package io.securityproject.users.controller;


import io.securityproject.users.dto.AccountDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RestApiLoginController {
    
    @GetMapping("/user")
    public AccountDto restUser(@AuthenticationPrincipal AccountDto accountDto) {
        return accountDto;
    }
    @GetMapping("/manager")
    public AccountDto restManager(@AuthenticationPrincipal AccountDto accountDto) {
        return accountDto;
    }
    @GetMapping("/admin")
    public AccountDto restAdmin(@AuthenticationPrincipal AccountDto accountDto) {
        return accountDto;
    }
}
