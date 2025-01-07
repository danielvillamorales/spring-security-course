package com.cursos.api.spring_security_course.persistance.repository;

import com.cursos.api.spring_security_course.persistance.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}