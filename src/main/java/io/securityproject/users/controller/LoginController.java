package io.securityproject.users.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    
    @GetMapping("/login")
    public String login(@RequestParam(value = "error",required = false) String error
                       , @RequestParam(value = "exception",required = false) String exception
                       , Model model
                        ) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "login/login";
    }
    
    @GetMapping("/signup")
    public String signup() {
        return "login/signup";
    }
    
    /**
     * Get 방식 로그아웃  SecurityContextLogoutHandler 내부에서 세션 무효화 및 authentication도 null로 만듬
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request,response,authentication);
        }
        return "redirect:/login";
    }
}
