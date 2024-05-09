package io.securityproject.users.dto;


import io.securityproject.users.entity.Account;
import io.securityproject.users.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    
    private Long id;
    private String username;
    private String password;
    private int age;
    private List<String> roles;
    
    
    public static AccountDto createAccountDto(Account account) {
        AccountDto accountDto = new AccountDto();
        accountDto.id = account.getId();
        accountDto.username = account.getUsername();
        accountDto.password = account.getPassword();
        accountDto.age = account.getAge();
        accountDto.roles = account.getUserRoles().stream()
                .map(Role::getRoleName)
                .collect(Collectors.toList());
        return accountDto;
    }
}
