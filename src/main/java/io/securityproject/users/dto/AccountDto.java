package io.securityproject.users.dto;


import lombok.Data;

@Data
public class AccountDto {

    private Long id;
    private String username;
    private String password;
    private  int age;
    private String roles;
    
    
    
}
