package com.cursos.api.spring_security_course.service.auth;

import com.cursos.api.spring_security_course.dto.AuthenticationRequest;
import com.cursos.api.spring_security_course.dto.AuthenticationResponse;
import com.cursos.api.spring_security_course.dto.RegisterUser;
import com.cursos.api.spring_security_course.dto.SaveUser;
import com.cursos.api.spring_security_course.exception.NotFoundException;
import com.cursos.api.spring_security_course.persistance.entity.User;
import com.cursos.api.spring_security_course.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;

@AllArgsConstructor
@Service
public class AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public RegisterUser registerOneCustomer(@Valid SaveUser newUser) {
        User user = userService.registerOneCustomer(newUser);
        return RegisterUser.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .role(user.getRole().name())
                .jwt(jwtService.generateToken(user, generatedExtraClaims(user)))
                .build();

    }

    private Map<String, Object> generatedExtraClaims(User user) {
        return Map.of("name", user.getName(),
                "role", user.getRole().name(),
                "authorities", user.getAuthorities());
    }

    public AuthenticationResponse login(@Valid AuthenticationRequest request) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword());
        authenticationManager.authenticate(authentication);
        User user = userService.findByUsername(request.getUsername());
        return AuthenticationResponse.builder()
                .jwt(jwtService.generateToken(user, generatedExtraClaims(user)))
                .build();
    }

    public Boolean validate(String token) {
        try {
            jwtService.extractUsername(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public User findLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getPrincipal().toString();
        return userService.findByUsername(username);

    }
}
