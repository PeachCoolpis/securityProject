package io.securityproject.security.manager;

import io.securityproject.admin.repository.ResourcesRepository;
import io.securityproject.security.mapper.PersistentUrlRoleMapper;
import io.securityproject.security.service.DynamicAuthorizationService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.expression.DefaultHttpSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcherEntry;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class CustomAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {
    
    private static final AuthorizationDecision ACCESS = new AuthorizationDecision(true);
   // private static final AuthorizationDecision ACCESS = new AuthorizationDecision(false);
    private final HandlerMappingIntrospector handlerMappingIntrospector;
    private List<RequestMatcherEntry<AuthorizationManager<RequestAuthorizationContext>>> mappings;
    private final ResourcesRepository repository;
    private final RoleHierarchyImpl roleHierarchy;
    DynamicAuthorizationService dynamicAuthorizationService;
    @PostConstruct
    public void mapping() {
        dynamicAuthorizationService = new DynamicAuthorizationService(new PersistentUrlRoleMapper(repository));
        setMapping();
        
    }
    
    private void setMapping() {
        Map<String, String> urlRoleMappings = dynamicAuthorizationService.getUrlRoleMappings();
        mappings = urlRoleMappings
                .entrySet().stream()
                .map(e -> new RequestMatcherEntry<>(
                        new MvcRequestMatcher(handlerMappingIntrospector, e.getKey()),
                        customAuthorizationManager(e.getValue())
                )).collect(Collectors.toList());
    }
    
    
    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext request) {
        for (RequestMatcherEntry<AuthorizationManager<RequestAuthorizationContext>> mapping : this.mappings) {
            
            RequestMatcher matcher = mapping.getRequestMatcher();
            RequestMatcher.MatchResult matchResult = matcher.matcher(request.getRequest());
            if (matchResult.isMatch()) {
                AuthorizationManager<RequestAuthorizationContext> manager = mapping.getEntry();
                return manager.check(authentication,
                        new RequestAuthorizationContext(request.getRequest(), matchResult.getVariables()));
            }
        }
        
        return ACCESS;
    }
    
    /**
     * ROLE 로 시작하면 AuthorityAuthorizationManager을 반환 그 외면 표현식 매니저를 반환
     * @param role
     * @return
     */
    private AuthorizationManager<RequestAuthorizationContext> customAuthorizationManager(String role) {
        if (role != null) {
            if (role.startsWith("ROLE")) {
                AuthorityAuthorizationManager<RequestAuthorizationContext> authorizationManager =
                        AuthorityAuthorizationManager.hasAuthority(role);
                authorizationManager.setRoleHierarchy(roleHierarchy);
                return authorizationManager;
            }else {
                DefaultHttpSecurityExpressionHandler handler =  new DefaultHttpSecurityExpressionHandler();
                handler.setRoleHierarchy(roleHierarchy);
                WebExpressionAuthorizationManager authorizationManager = new WebExpressionAuthorizationManager(role);
                authorizationManager.setExpressionHandler(handler);
                return authorizationManager;
            }
        }
        return null;
    }
    
    @Override
    public void verify(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        AuthorizationManager.super.verify(authentication, object);
    }
    
    public synchronized void reload() {
        this.mappings.clear();
        setMapping();
    }
}
