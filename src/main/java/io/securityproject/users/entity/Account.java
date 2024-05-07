package io.securityproject.users.entity;


import io.securityproject.users.dto.AccountDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private  int age;
    private String roles;
    
    
    public static Account createAccount(AccountDto accountDto) {
        Account account = new Account();
        account.age = accountDto.getAge();
        account.username = accountDto.getUsername();
        account.roles = accountDto.getRoles();
        return account;
    }
}
