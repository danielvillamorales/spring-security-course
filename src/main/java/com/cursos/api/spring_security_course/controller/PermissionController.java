package com.cursos.api.spring_security_course.controller;

import com.cursos.api.spring_security_course.dto.SavePermission;
import com.cursos.api.spring_security_course.dto.ShowPermission;
import com.cursos.api.spring_security_course.service.PermissionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/permissions")
public class PermissionController {


    private final PermissionService permissionService;

    @GetMapping
    public ResponseEntity<Page<ShowPermission>> findAll(Pageable pageable){

        Page<ShowPermission> permissions = permissionService.findAll(pageable);

        if(permissions.hasContent()){
            return ResponseEntity.ok(permissions);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{permissionId}")
    public ResponseEntity<ShowPermission> findOneById(@PathVariable Long permissionId){
        Optional<ShowPermission> permission = permissionService.findOneById(permissionId);

        if(permission.isPresent()){
            return ResponseEntity.ok(permission.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ShowPermission> createOne(@RequestBody @Valid SavePermission savePermission){
        ShowPermission permission = permissionService.createOne(savePermission);
        return ResponseEntity.status(HttpStatus.CREATED).body(permission);
    }

    @DeleteMapping("/{permissionId}")
    public ResponseEntity<ShowPermission> deleteOneById(@PathVariable Long permissionId){
        ShowPermission permission = permissionService.deleteOneById(permissionId);
        return ResponseEntity.ok(permission);
    }

}