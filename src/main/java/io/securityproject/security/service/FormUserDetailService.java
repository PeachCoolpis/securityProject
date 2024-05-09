package io.securityproject.security.service;

import io.securityproject.users.dto.AccountContext;
import io.securityproject.users.dto.AccountDto;
import io.securityproject.users.entity.Account;
import io.securityproject.users.entity.Role;
import io.securityproject.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service(value = "userDetailsService") // 빈이름 클래스 이름은 들어가지 않도록 왜냐하면 InMemoryUserDetailsManager 도 주입대상이기떄문에
@RequiredArgsConstructor
public class FormUserDetailService implements UserDetailsService {
    
    private final UserRepository repository;
    
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Account account = repository.findByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException("사용자 없음 : " + username);
        }
        
        
        List<GrantedAuthority> authorities = account.getUserRoles()
                .stream()
                .map(Role::getRoleName)
                .collect(Collectors.toSet())
                .stream()
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        
        
        AccountDto accountDto = AccountDto.createAccountDto(account);
        
        return new AccountContext(accountDto, authorities); // 어댑터 패턴 AccountContext는 UserDetails를 구현한 클래스로서 AccountDto를 Wrapping함
    }
}
