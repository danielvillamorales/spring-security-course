package com.cursos.api.spring_security_course;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpringSecurityCourseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityCourseApplication.class, args);
	}

	@Bean
	public CommandLineRunner createPasswordEncoder(PasswordEncoder passwordEncoder) {
		return args -> {
			System.out.println("Password: " + passwordEncoder.encode("12345678"));
			System.out.println("Password: " + passwordEncoder.encode("87654321"));
			System.out.println("Password: " + passwordEncoder.encode("00000000"));
		};
	}
}
