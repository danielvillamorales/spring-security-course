package com.cursos.api.spring_security_course.persistance.repository;

import com.cursos.api.spring_security_course.persistance.entity.Role;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
