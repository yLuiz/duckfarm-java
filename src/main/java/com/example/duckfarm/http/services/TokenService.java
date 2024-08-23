package com.example.duckfarm.http.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.example.duckfarm.db.model.Customer;

@Service
public class TokenService {

    @Value("${api.token}")
    private String jwtSecret;

    public String generateToken(Customer customer) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            String token = JWT.create()
                    .withIssuer("duckfarm")
                    .withSubject(customer.getEmail())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);

            return token;
        } catch (JWTCreationException e) {
            throw new RuntimeException("Error generating token", e);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            return JWT.require(algorithm)
                    .withIssuer("duckfarm")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTCreationException e) {

            throw new RuntimeException("Invalid token", e);
        }
    }

    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(24).toInstant(ZoneOffset.of("-03:00"));
    }
}
