package io.securityproject.users.controller;


import io.securityproject.users.dto.AccountDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccessDeniedController {
    
    @GetMapping("/denied")
    public String denied(@RequestParam(value = "exception", required = false) String exception,
                         @AuthenticationPrincipal AccountDto accountDto,
                         Model model
    ) {
        model.addAttribute("username",accountDto.getUsername());
        model.addAttribute("exception", exception);
        return "login/denied";
    }
}
