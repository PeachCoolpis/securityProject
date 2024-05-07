package io.securityproject.users.service;


import io.securityproject.users.entity.Account;
import io.securityproject.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    
    private final UserRepository userRepository;
    
    @Override
    public void createUser(Account account) {
        userRepository.save(account);
    }
}
