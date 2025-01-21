package com.cursos.api.spring_security_course.service;

import com.cursos.api.spring_security_course.persistance.entity.Role;

public interface RoleService {
    Role findDefaultRole();
}
