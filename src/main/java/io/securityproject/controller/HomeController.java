package io.securityproject.controller;


import io.securityproject.users.dto.AccountContext;
import io.securityproject.users.dto.AccountDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@GetMapping(value = "/")
	public String dashboard() {
		return "/dashboard";
	}
	
	@GetMapping(value = "/user")
	public String user() {
		return "/user";
	}
	
	@GetMapping(value = "/manager")
	public String manager() {
		return "/manager";
	}
	
	@GetMapping(value = "/admin")
	public String admin() {
		return "/admin";
	}
	
	@GetMapping("/api")
	public String restDashBoard() {
		return "rest/dashboard";
	}
}