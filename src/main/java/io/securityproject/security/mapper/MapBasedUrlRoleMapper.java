package io.securityproject.security.mapper;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapBasedUrlRoleMapper implements UrlRoleMapper{
    
    private final LinkedHashMap<String, String> urlRoleMappings = new LinkedHashMap<>();
    
    @Override
    public Map<String, String> getRoleMappings() {
        
        urlRoleMappings.put("/", "permitAll");
        urlRoleMappings.put("/css/**", "permitAll");
        urlRoleMappings.put("/js/**", "permitAll");
        urlRoleMappings.put("/images/**", "permitAll");
        urlRoleMappings.put("/favicon.*", "permitAll");
        urlRoleMappings.put("/*/icon-*", "permitAll");
        urlRoleMappings.put("/signup", "permitAll");
        urlRoleMappings.put("/login", "permitAll");
        urlRoleMappings.put("/logout", "permitAll");
        urlRoleMappings.put("/denied", "authenticated");
        urlRoleMappings.put("/user", "ROLE_USER");
        urlRoleMappings.put("/admin/**", "ROLE_ADMIN");
        urlRoleMappings.put("/manager", "ROLE_MANAGER");
        urlRoleMappings.put("/db", "hasRole('DBA')");

        return urlRoleMappings;
    }
}
