package io.securityproject.security.mapper;

import io.securityproject.admin.repository.ResourcesRepository;
import io.securityproject.users.entity.Resources;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PersistentUrlRoleMapper implements UrlRoleMapper{
    
    private final LinkedHashMap<String, String> urlRoleMappings = new LinkedHashMap<>();
    private final ResourcesRepository repository;
    
    public PersistentUrlRoleMapper(ResourcesRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Map<String, String> getRoleMappings() {
        urlRoleMappings.clear();
        List<Resources> resources = repository.findAllResources();
        
        resources
                .forEach(re -> {
                    re.getRoleSet().forEach(role -> {
                        urlRoleMappings.put(re.getResourceName(), role.getRoleName());
                    });
                });
        
        return urlRoleMappings;
    }
}
