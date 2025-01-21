package com.cursos.api.spring_security_course.persistance.repository;

import com.cursos.api.spring_security_course.persistance.entity.Operation;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {

    @Query("SELECT o FROM Operation o WHERE o.permitAll = true")
    List<Operation> findByPublicAcces();

    Optional<Operation> findByName(@NotBlank String operation);
}
