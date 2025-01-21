package com.cursos.api.spring_security_course.service.impl;

import com.cursos.api.spring_security_course.exception.NotFoundException;
import com.cursos.api.spring_security_course.persistance.entity.Role;
import com.cursos.api.spring_security_course.persistance.repository.RoleRepository;
import com.cursos.api.spring_security_course.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Value("${security.default.role}")
    private String defaultRole;

    @Override
    public Role findDefaultRole() {
        return roleRepository.findByName(defaultRole).orElseThrow(() -> new NotFoundException("Role not found"));
    }
}
