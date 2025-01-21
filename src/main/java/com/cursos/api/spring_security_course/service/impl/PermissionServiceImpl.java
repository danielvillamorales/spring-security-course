package com.cursos.api.spring_security_course.service.impl;


import com.cursos.api.spring_security_course.dto.SavePermission;
import com.cursos.api.spring_security_course.dto.ShowPermission;
import com.cursos.api.spring_security_course.exception.NotFoundException;
import com.cursos.api.spring_security_course.persistance.entity.GrantedPermission;
import com.cursos.api.spring_security_course.persistance.entity.Operation;
import com.cursos.api.spring_security_course.persistance.entity.Role;
import com.cursos.api.spring_security_course.persistance.repository.OperationRepository;
import com.cursos.api.spring_security_course.persistance.repository.PermissionRepository;
import com.cursos.api.spring_security_course.persistance.repository.RoleRepository;
import com.cursos.api.spring_security_course.service.PermissionService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class PermissionServiceImpl implements PermissionService {


    private PermissionRepository permissionRepository;


    private RoleRepository roleRepository;


    private OperationRepository operationRepository;

    @Override
    public Page<ShowPermission> findAll(Pageable pageable) {
        return permissionRepository.findAll(pageable)
                .map(this::mapEntityToShowDto);
    }

    private ShowPermission mapEntityToShowDto(GrantedPermission grantedPermission) {
        if(grantedPermission == null) return null;

        ShowPermission showDto = new ShowPermission();
        showDto.setId(grantedPermission.getId());
        showDto.setRole(grantedPermission.getRole().getName());
        showDto.setOperation(grantedPermission.getOperation().getName());
        showDto.setHttpMethod(grantedPermission.getOperation().getHttpMethod());
        showDto.setModule(grantedPermission.getOperation().getModule().getName());

        return showDto;
    }

    @Override
    public Optional<ShowPermission> findOneById(Long permissionId) {
        return permissionRepository.findById(permissionId)
                .map(this::mapEntityToShowDto);
    }

    @Override
    public ShowPermission createOne(SavePermission savePermission) {

        GrantedPermission newPermission = new GrantedPermission();

        Operation operation = operationRepository.findByName(savePermission.getOperation())
                        .orElseThrow(() -> new NotFoundException("Operation not found. Operation: " + savePermission.getOperation()));
        newPermission.setOperation(operation);

        Role role = roleRepository.findByName(savePermission.getRole()).orElseThrow(
                () -> new NotFoundException("Role not found. Role: " + savePermission.getRole()));
        newPermission.setRole(role);

        permissionRepository.save(newPermission);
        return this.mapEntityToShowDto(newPermission);
    }

    @Override
    public ShowPermission deleteOneById(Long permissionId) {
        GrantedPermission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new NotFoundException("Permission not found. Permission: " + permissionId));

        permissionRepository.delete(permission);

        return this.mapEntityToShowDto(permission);
    }
}
