package com.cursos.api.spring_security_course.service.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class JwtService {

    @Value("${security.jwt.expiration-in-minutes}")
    private Long expirationInMinutes;

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    public String generateToken(UserDetails user, Map<String, Object> extraClaims) {
        return Jwts.builder()
                .header().type("JWT").and()
                .claims(extraClaims)
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + (expirationInMinutes * 60000)))
                .signWith(generateKey(), SIG.HS256)
                .compact();
    }

    private SecretKey generateKey() {
        byte[] key = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(key);
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(generateKey()).build().parseSignedClaims(token).getPayload();
    }
}
