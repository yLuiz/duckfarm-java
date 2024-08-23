package com.example.duckfarm.http.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.duckfarm.db.model.Customer;
import com.example.duckfarm.http.services.CustomerService;
import com.example.duckfarm.http.services.TokenService;
import com.example.duckfarm.shared.dto.input.AuthenticationDTO;
import com.example.duckfarm.shared.dto.input.CreateCustomerDTO;
import com.example.duckfarm.shared.dto.output.LoginResponseDTO;
import com.example.duckfarm.shared.dto.output.ResponseError;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("auth")
@Tag(name = "Authentication Controller", description = "Endpoint to make authentication.")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("login")
    @Operation(summary = "Make authentication", description = "Get token to make requests.")
    public ResponseEntity<LoginResponseDTO> authenticate(@RequestBody @Valid AuthenticationDTO payload) {

        var emailPassword = new UsernamePasswordAuthenticationToken(payload.getEmail(), payload.getPassword());
        var auth = authenticationManager.authenticate(emailPassword);

        var token = tokenService.generateToken((Customer) auth.getPrincipal());

        return new ResponseEntity<>(new LoginResponseDTO(token), HttpStatus.OK);
    }

    @PostMapping("register")
    @Operation(summary = "Create Customer", description = "Creates a new customer.")
    public ResponseEntity<?> create(@RequestBody @Valid CreateCustomerDTO payload) {
        try {
            return new ResponseEntity<>(customerService.create(payload), HttpStatus.CREATED);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(new ResponseError(e.getReason(), e.getStatusCode()), e.getStatusCode());
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseError(e.getMessage(),  HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }

}
