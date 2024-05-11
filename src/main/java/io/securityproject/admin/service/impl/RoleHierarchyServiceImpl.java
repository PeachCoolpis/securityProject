package io.securityproject.admin.service.impl;


import io.securityproject.admin.repository.RoleHierarchyRepository;
import io.securityproject.admin.service.RoleHierarchyService;
import io.securityproject.users.entity.RoleHierarchy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

@Service
public class RoleHierarchyServiceImpl implements RoleHierarchyService {
    private RoleHierarchyRepository roleHierarchyRepository;
    
    @Autowired
    private void setRoleHierarchyServiceImpl(RoleHierarchyRepository roleHierarchyRepository) {
        this.roleHierarchyRepository = roleHierarchyRepository;
    }
    
    @Transactional
    @Override
    public String findAllHierarchy() {
        
        List<RoleHierarchy> rolesHierarchy = roleHierarchyRepository.findAll();
        
        Iterator<RoleHierarchy> itr = rolesHierarchy.iterator();
        StringBuilder hierarchyRole = new StringBuilder();
        
        while (itr.hasNext()) {
            RoleHierarchy roleHierarchy = itr.next();
            if (roleHierarchy.getParent() != null) {
                hierarchyRole.append(roleHierarchy.getParent().getRoleName());
                hierarchyRole.append(" > ");
                hierarchyRole.append(roleHierarchy.getRoleName());
                hierarchyRole.append("\n");
            }
        }
        return hierarchyRole.toString();
    }
}