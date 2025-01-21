package com.cursos.api.spring_security_course.config.security.authorization;

import com.cursos.api.spring_security_course.persistance.entity.GrantedPermission;
import com.cursos.api.spring_security_course.persistance.entity.Operation;
import com.cursos.api.spring_security_course.persistance.entity.User;
import com.cursos.api.spring_security_course.persistance.repository.OperationRepository;
import com.cursos.api.spring_security_course.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.AuthorizationResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Pattern;

@AllArgsConstructor
@Component
@Slf4j
public class CustomAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final OperationRepository operationRepository;
    private final UserService userService;

    @Override
    public void verify(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        AuthorizationManager.super.verify(authentication, object);
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication,
                                       RequestAuthorizationContext requestContext) {

        HttpServletRequest request = requestContext.getRequest();
        log.info(request.getRequestURI());
        String url = extractUrl(request);
        String method = request.getMethod();
        boolean isPublic = isPublic(url, method);
        if (isPublic) {
            return new AuthorizationDecision(true);
        }
        return  new AuthorizationDecision(isGranted(url, method, authentication.get()));

    }

    private String extractUrl(HttpServletRequest requestURI) {
        String contextPath = requestURI.getContextPath();
        return requestURI.getRequestURI().replace(contextPath, "");
    }

    private boolean isPublic(String url, String httpMethod) {
        List<Operation> publicAccessEndpoints = operationRepository.findByPublicAcces();
        return publicAccessEndpoints.stream()
                .anyMatch(getOperationPredicate(url, httpMethod));

    }

    private boolean isGranted(String url, String httpMethod, Authentication authentication) {
        if (!(authentication instanceof UsernamePasswordAuthenticationToken)) {
            throw new AuthenticationCredentialsNotFoundException("No se encontro un usuario autenticado");
        }

        List<Operation> operations = obtainedOperations(authentication);
        return operations.stream()
                .anyMatch(getOperationPredicate(url, httpMethod));
    }

    private static Predicate<Operation> getOperationPredicate(String url, String httpMethod) {
        return operation -> operation.getHttpMethod().equals(httpMethod)
                && Pattern.compile(
                        operation.getModule().getBasePath().concat(operation.getPath())
                ).matcher(url)
                .matches();
    }


    private List<Operation> obtainedOperations(Authentication authentication) {
        UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) authentication;
        String username = (String) authToken.getPrincipal();
        User user = userService.findByUsername(username);
        return user.getRole().getPermissions().stream().map(GrantedPermission::getOperation).toList();
    }

    @Override
    public AuthorizationResult authorize(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        return AuthorizationManager.super.authorize(authentication, object);
    }
}
