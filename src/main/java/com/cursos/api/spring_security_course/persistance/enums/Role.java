package com.cursos.api.spring_security_course.persistance.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;


@AllArgsConstructor
@Getter
public enum Role {
    ROLE_ADMINISTRATOR(
            List.of(
                    RolePermission.READ_ALL_PRODUCTS,
                    RolePermission.READ_ONE_PRODUCT,
                    RolePermission.CREATE_ONE_PRODUCT,
                    RolePermission.UPDATE_ONE_PRODUCT,
                    RolePermission.DISABLE_ONE_PRODUCT,
                    RolePermission.READ_ALL_CATEGORIES,
                    RolePermission.READ_ONE_CATEGORY,
                    RolePermission.CREATE_ONE_CATEGORY,
                    RolePermission.UPDATE_ONE_CATEGORY,
                    RolePermission.DISABLE_ONE_CATEGORY,
                    RolePermission.READ_MY_PROFILE
            )),
    ROLE_ASSISTANT_ADMINISTRATOR(
            List.of(
                    RolePermission.READ_ALL_PRODUCTS,
                    RolePermission.READ_ONE_PRODUCT,
                    RolePermission.UPDATE_ONE_PRODUCT,
                    RolePermission.READ_ALL_CATEGORIES,
                    RolePermission.READ_ONE_CATEGORY,
                    RolePermission.UPDATE_ONE_CATEGORY,
                    RolePermission.READ_MY_PROFILE
            )
    ),
    ROLE_CUSTOMER(
            List.of(
                    RolePermission.READ_MY_PROFILE
            )
    );

    private final List<RolePermission> permissions;
}
