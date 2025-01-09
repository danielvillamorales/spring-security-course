package com.cursos.api.spring_security_course.service.auth;

import com.cursos.api.spring_security_course.dto.RegisterUser;
import com.cursos.api.spring_security_course.dto.SaveUser;
import com.cursos.api.spring_security_course.persistance.entity.User;
import com.cursos.api.spring_security_course.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@AllArgsConstructor
@Service
public class AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;

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
}
