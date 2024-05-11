package io.securityproject.admin.repository;


import io.securityproject.users.entity.RoleHierarchy;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleHierarchyRepository extends JpaRepository<RoleHierarchy, Long> {
}
