package com.cursos.api.spring_security_course.service;

import com.cursos.api.spring_security_course.dto.SaveUser;
import com.cursos.api.spring_security_course.persistance.entity.User;
import jakarta.validation.Valid;

public interface UserService {
    User registerOneCustomer(@Valid SaveUser newUser);
}
