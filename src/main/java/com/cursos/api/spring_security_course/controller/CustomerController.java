package com.cursos.api.spring_security_course.controller;

import com.cursos.api.spring_security_course.dto.RegisterUser;
import com.cursos.api.spring_security_course.dto.SaveUser;
import com.cursos.api.spring_security_course.service.auth.AuthenticationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<RegisterUser> registerOne(@RequestBody @Valid SaveUser newUser){
        RegisterUser registerUser = authenticationService.registerOneCustomer(newUser);
        return new ResponseEntity<>(registerUser, HttpStatus.CREATED);
    }
}
