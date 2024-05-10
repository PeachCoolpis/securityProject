package io.securityproject.security.mapper;

import java.util.Map;

public interface UrlRoleMapper {
    /**
     * URL , 권한
     * @return
     */
    Map<String, String> getRoleMappings(); 
}
