package com.cursos.api.spring_security_course.service.impl;

import com.cursos.api.spring_security_course.dto.SaveUser;
import com.cursos.api.spring_security_course.exception.InvalidPasswordencoder;
import com.cursos.api.spring_security_course.persistance.entity.User;
import com.cursos.api.spring_security_course.persistance.enums.Role;
import com.cursos.api.spring_security_course.persistance.repository.UserRepository;
import com.cursos.api.spring_security_course.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public User registerOneCustomer(SaveUser newUser) {
        validatePassword(newUser);
        return userRepository.save(
                User.builder()
                        .name(newUser.getName())
                        .username(newUser.getUsername())
                        .role(Role.ROLE_CUSTOMER)
                        .password(passwordEncoder.encode(newUser.getPassword()))
                        .build()
        );
    }

    private void validatePassword(SaveUser newUser) {
        if (!StringUtils.hasText(newUser.getPassword()) || !StringUtils.hasText(newUser.getRepeatedPassword())) {
            throw new InvalidPasswordencoder("Password is required");
        }

        if (!newUser.getPassword().equals(newUser.getRepeatedPassword())) {
            throw new InvalidPasswordencoder("Password and repit password must be equals");
        }
    }
}
