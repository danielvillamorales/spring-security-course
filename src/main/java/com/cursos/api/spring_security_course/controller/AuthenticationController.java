package com.cursos.api.spring_security_course.controller;

import com.cursos.api.spring_security_course.dto.AuthenticationRequest;
import com.cursos.api.spring_security_course.dto.AuthenticationResponse;
import com.cursos.api.spring_security_course.service.auth.AuthenticationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validate(@RequestParam String token){
        return new ResponseEntity<>(authenticationService.validate(token), HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest request) {
        return new ResponseEntity<>(authenticationService.login(request), HttpStatus.OK);
    }
}
