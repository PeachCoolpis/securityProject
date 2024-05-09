package io.securityproject.admin.service;



import io.securityproject.users.dto.AccountDto;
import io.securityproject.users.entity.Account;

import java.util.List;

public interface UserManagementService {

    void modifyUser(AccountDto accountDto);

    List<Account> getUsers();
    AccountDto getUser(Long id);

    void deleteUser(Long idx);

}
