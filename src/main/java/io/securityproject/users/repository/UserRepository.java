package io.securityproject.users.repository;

import io.securityproject.users.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Account,Long> {
}
