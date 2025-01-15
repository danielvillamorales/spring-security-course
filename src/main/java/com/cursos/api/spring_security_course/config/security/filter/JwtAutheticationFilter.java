package com.cursos.api.spring_security_course.config.security.filter;

import com.cursos.api.spring_security_course.service.UserService;
import com.cursos.api.spring_security_course.service.auth.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@AllArgsConstructor
@Component
public class JwtAutheticationFilter extends OncePerRequestFilter {

    private final UserService userService;

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.info("Entro a mi filtro Request to {}", request.getRequestURI());
        /**
         * obtener encabezado http llamado authorization
         */
        String authorizationHeader = request.getHeader("Authorization");
        if (!StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        /**
         * obtener el JWT desde el encabezado
         */
        String jwt = authorizationHeader.split(" ")[1];
        /**
         * obtener el subject/username desde el token
         */
        String username = jwtService.extractUsername(jwt);
        /**
         * setear el objeto autentication dentro de security contextholder
         */
        UsernamePasswordAuthenticationToken authtoken = new UsernamePasswordAuthenticationToken(username,
                null, userService.findByUsername(username).getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authtoken);
        authtoken.setDetails(new WebAuthenticationDetails(request));
        /**
         * ejecutar el resto de filtros
         */
        filterChain.doFilter(request, response);
    }
}
