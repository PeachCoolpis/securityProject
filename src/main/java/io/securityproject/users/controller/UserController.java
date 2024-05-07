package io.securityproject.users.controller;


import io.securityproject.users.dto.AccountDto;
import io.securityproject.users.entity.Account;
import io.securityproject.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {
    
    private final PasswordEncoder passwordEncoder;
    private final UserService service;
    
    @PostMapping("/signup")
    public String signup(AccountDto accountDto) {
        
        Account account = Account.createAccount(accountDto);
        account.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        service.createUser(account);
        return "redirect:/";
    }
}
