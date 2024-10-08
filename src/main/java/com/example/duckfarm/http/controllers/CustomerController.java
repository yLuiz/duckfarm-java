package com.example.duckfarm.http.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.duckfarm.db.model.Customer;
import com.example.duckfarm.http.services.CustomerService;
import com.example.duckfarm.shared.dto.input.CreateCustomerDTO;
import com.example.duckfarm.shared.dto.output.ResponseError;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("customer")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Customer Controller", description = "Endpoints to make operations about customers.")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping()
    @Operation(summary = "Get All Customers", description = "Returns a list of customers registered.")
    public Page<Customer> findAll(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size
    ) {
        if (page == null || size == null) {
            page = 0;
            size = 10;
        }
        return customerService.findAll(page, size);
    }

    @GetMapping("{id}")
    @Operation(summary = "Get a Customer by id", description = "Returns a customer by id.")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        try {
            Customer customer = customerService.findById(id);

            return new ResponseEntity<>(customer, HttpStatus.OK);
        }
        catch (ResponseStatusException e) {
            return new ResponseEntity<>(new ResponseError(e.getReason(), e.getStatusCode()), e.getStatusCode());
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseError(e.getMessage(),  HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("{id}")
    @Operation(summary = "Update a Customer", description = "Updates a customer by id.")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody @Valid CreateCustomerDTO payload) {
        try {
            Customer customerUpdated = customerService.update(id, payload);
            return new ResponseEntity<>(customerUpdated, HttpStatus.OK);
        }
        catch (ResponseStatusException e) {
            return new ResponseEntity<>(new ResponseError(e.getReason(), e.getStatusCode()), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return new ResponseEntity<>(new ResponseError(e.getMessage(),  HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete a Customer", description = "Delete a customer by id.")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            Customer customerUpdated = customerService.delete(id);
            return new ResponseEntity<>(customerUpdated, HttpStatus.OK);
        }
        catch (ResponseStatusException e) {
            return new ResponseEntity<>(new ResponseError(e.getReason(), e.getStatusCode()), e.getStatusCode());
        }
        catch (Exception e) {
            return new ResponseEntity<>(new ResponseError(e.getMessage(),  HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);        
        }
        
    }
}
