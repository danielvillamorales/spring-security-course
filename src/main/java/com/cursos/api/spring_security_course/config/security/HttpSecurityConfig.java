package com.cursos.api.spring_security_course.config.security;

import com.cursos.api.spring_security_course.config.security.filter.JwtAutheticationFilter;
import com.cursos.api.spring_security_course.persistance.enums.Role;
import com.cursos.api.spring_security_course.persistance.enums.RolePermission;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
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

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS
                ))
                .authenticationProvider(daoAuthenticationProvider)
                .addFilterBefore(jwtAutheticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(
                        HttpSecurityConfig::buildRequestMatchers
                ) // si deseo manejar los permisos por anotaciones de permit all comerntar esta linea
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
                .hasAnyRole(Role.ADMINISTRATOR.name(), Role.ASSISTANT_ADMINISTRATOR.name());
//        auth.requestMatchers(HttpMethod.GET, "/products/{productId}")
        auth.requestMatchers(RegexRequestMatcher.regexMatcher(HttpMethod.GET, "/products/[0-9]*"))
                .hasAnyRole(Role.ADMINISTRATOR.name(), Role.ASSISTANT_ADMINISTRATOR.name());
        auth.requestMatchers(HttpMethod.POST, "/products")
                .hasAuthority(RolePermission.CREATE_ONE_PRODUCT.name());
        auth.requestMatchers(HttpMethod.PUT, "/products/{productId}")
                .hasAuthority(RolePermission.UPDATE_ONE_PRODUCT.name());
        auth.requestMatchers(HttpMethod.PUT, "/products/{productId}/disabled")
                .hasAuthority(RolePermission.DISABLE_ONE_PRODUCT.name());
        /**
         * autorizacion endpoints de categories.
         */
        auth.requestMatchers(HttpMethod.GET, "/categories")
                .hasAuthority(RolePermission.READ_ALL_CATEGORIES.name());
        auth.requestMatchers(HttpMethod.GET, "/categories/{categoryId}")
                .hasAuthority(RolePermission.READ_ONE_CATEGORY.name());
        auth.requestMatchers(HttpMethod.POST, "/categories")
                .hasAuthority(RolePermission.CREATE_ONE_CATEGORY.name());
        auth.requestMatchers(HttpMethod.PUT, "/categories/{categoryId}")
                .hasAuthority(RolePermission.UPDATE_ONE_CATEGORY.name());
        auth.requestMatchers(HttpMethod.PUT, "/categories/{categoryId}/disabled")
                .hasAuthority(RolePermission.DISABLE_ONE_CATEGORY.name());
        auth.requestMatchers(HttpMethod.GET, "/auth/profile")
                .hasAuthority(RolePermission.READ_MY_PROFILE.name());
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
