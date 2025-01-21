package com.cursos.api.spring_security_course.config.security;

import com.cursos.api.spring_security_course.config.security.filter.JwtAutheticationFilter;
import com.cursos.api.spring_security_course.persistance.enums.RoleEnum;
import com.cursos.api.spring_security_course.persistance.enums.RolePermissionEnum;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
//@EnableMethodSecurity(prePostEnabled = true)
public class HttpSecurityConfig {

    private final AuthenticationProvider daoAuthenticationProvider;
    private final JwtAutheticationFilter jwtAutheticationFilter;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AccessDeniedHandler accessDeniedHandler;

    //dinamico permisos
    private final AuthorizationManager<RequestAuthorizationContext> authorizationManager;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS
                ))
                .authenticationProvider(daoAuthenticationProvider)
                .addFilterBefore(jwtAutheticationFilter, UsernamePasswordAuthenticationFilter.class)
                //.authorizeHttpRequests(
                //        HttpSecurityConfig::buildRequestMatchers
                //) // si deseo manejar los permisos por anotaciones de permit all comerntar esta linea
                .authorizeHttpRequests(auth -> auth.anyRequest().access(authorizationManager))
                .exceptionHandling(exceptionconfig -> {
                            exceptionconfig.authenticationEntryPoint(authenticationEntryPoint);
                            exceptionconfig.accessDeniedHandler(accessDeniedHandler);
                        }
                )
                .build();
    }

    private static void buildRequestMatchers(
            AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth) {
        /**
         * autorizacion endpoints de productos.
         */
        auth.requestMatchers(HttpMethod.GET, "/products")
                .hasAnyRole(RoleEnum.ADMINISTRATOR.name(), RoleEnum.ASSISTANT_ADMINISTRATOR.name());
//        auth.requestMatchers(HttpMethod.GET, "/products/{productId}")
        auth.requestMatchers(RegexRequestMatcher.regexMatcher(HttpMethod.GET, "/products/[0-9]*"))
                .hasAnyRole(RoleEnum.ADMINISTRATOR.name(), RoleEnum.ASSISTANT_ADMINISTRATOR.name());
        auth.requestMatchers(HttpMethod.POST, "/products")
                .hasAuthority(RolePermissionEnum.CREATE_ONE_PRODUCT.name());
        auth.requestMatchers(HttpMethod.PUT, "/products/{productId}")
                .hasAuthority(RolePermissionEnum.UPDATE_ONE_PRODUCT.name());
        auth.requestMatchers(HttpMethod.PUT, "/products/{productId}/disabled")
                .hasAuthority(RolePermissionEnum.DISABLE_ONE_PRODUCT.name());

         //autorizacion endpoints de categories.

        auth.requestMatchers(HttpMethod.GET, "/categories")
                .hasAuthority(RolePermissionEnum.READ_ALL_CATEGORIES.name());
        auth.requestMatchers(HttpMethod.GET, "/categories/{categoryId}")
                .hasAuthority(RolePermissionEnum.READ_ONE_CATEGORY.name());
        auth.requestMatchers(HttpMethod.POST, "/categories")
                .hasAuthority(RolePermissionEnum.CREATE_ONE_CATEGORY.name());
        auth.requestMatchers(HttpMethod.PUT, "/categories/{categoryId}")
                .hasAuthority(RolePermissionEnum.UPDATE_ONE_CATEGORY.name());
        auth.requestMatchers(HttpMethod.PUT, "/categories/{categoryId}/disabled")
                .hasAuthority(RolePermissionEnum.DISABLE_ONE_CATEGORY.name());
        auth.requestMatchers(HttpMethod.GET, "/auth/profile")
                .hasAuthority(RolePermissionEnum.READ_MY_PROFILE.name());
        /**
         * autorizacion endpoints de publicos.
         */
        auth.requestMatchers(HttpMethod.POST, "/customers").permitAll();
        auth.requestMatchers(HttpMethod.POST, "/auth/authenticate").permitAll();
        auth.requestMatchers(HttpMethod.GET, "/auth/validate").permitAll();
        /**
         * autorizacion endpoints de administracion.
         */
        auth.anyRequest().authenticated();
    }

    private static void buildRequestMatchersV2(
            AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth) {
        /**
         * autorizacion endpoints de publicos.
         */
        auth.requestMatchers(HttpMethod.POST, "/customers").permitAll();
        auth.requestMatchers(HttpMethod.POST, "/auth/authenticate").permitAll();
        auth.requestMatchers(HttpMethod.GET, "/auth/validate").permitAll();
        /**
         * autorizacion endpoints de administracion.
         */
        auth.anyRequest().authenticated();
    }


}
