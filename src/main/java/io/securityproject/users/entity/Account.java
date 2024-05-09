package io.securityproject.users.entity;


import io.securityproject.users.dto.AccountDto;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private String username;
    
    @Column
    private int age;
    
    @Column
    private String password;
    
    @ManyToMany(fetch = FetchType.LAZY, cascade={CascadeType.MERGE})
    @JoinTable(name = "account_roles", joinColumns = { @JoinColumn(name = "account_id") }, inverseJoinColumns = {
            @JoinColumn(name = "role_id") })
    @ToString.Exclude
    private Set<Role> userRoles = new HashSet<>();
    
    
    public static Account createAccount(AccountDto accountDto) {
        Account account = new Account();
        account.age = accountDto.getAge();
        account.username = accountDto.getUsername();
        account.userRoles =  new HashSet<>(account.getUserRoles());
        return account;
    }
}
