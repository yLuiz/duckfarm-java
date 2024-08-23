package com.example.duckfarm.http.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.duckfarm.db.model.Duck;
import com.example.duckfarm.http.services.DuckService;
import com.example.duckfarm.shared.dto.input.CreateDuckDTO;
import com.example.duckfarm.shared.dto.output.DuckResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("duck")
@Tag(name = "Duck Controller", description = "Endpoints to make operations about ducks.")
public class DuckController {


    @Autowired
    private DuckService duckService;

    @PostMapping
    @Operation(summary = "Create Duck", description = "Creates a new duck.")
    public ResponseEntity<Duck> create(
        @Valid @RequestBody CreateDuckDTO body
    ) {

        Duck duck = duckService.create(body);
        return new ResponseEntity<>(duck, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get All Ducks", description = "Returns a list of ducks registered.")
    public Page<DuckResponseDTO> getAll(
        @RequestParam(value = "page", required=false) Integer page,
        @RequestParam(value = "size", required=false) Integer size
    ) {

        if (page == null ||  size == null ) {
            page = 0;
            size = 10;
        }

        Page<Duck> ducks = duckService.findAll(page, size);
        Page<DuckResponseDTO> ducksResponse = ducks.map(DuckResponseDTO::new);

        return ducksResponse;
    }

    @GetMapping("{id}")
    @Operation(summary = "Get One Duck by Id", description = "Returns a duck by id.")
    public ResponseEntity<DuckResponseDTO> getById(@PathVariable("id") Long id) {

        try {
            Duck duck = duckService.findById(id);

            if (duck == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }

            DuckResponseDTO duckResponse = new DuckResponseDTO(duck);

            return new ResponseEntity<>(duckResponse, HttpStatus.OK);
            
        } 
        catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), "Pato não encontrado");
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    
    @PutMapping("{id}")
    @Operation(summary = "Update Duck by Id", description = "Updates a duck by id.")
    public ResponseEntity<Duck> update(@PathVariable("id") Long id, @Valid @RequestBody CreateDuckDTO body) {
        try {
            Duck duckUpdated = duckService.update(id, body);

            return new ResponseEntity<>(duckUpdated, HttpStatus.OK);

        }
        catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
        }
    }

    @PatchMapping("{id}") 
    @Operation(summary = "Update Duck Mother to null", description = "Turn duck mother to null by id.")
    public ResponseEntity<Duck> patchMotherToNull(@PathVariable("id") Long id) {
        try {
            Duck patchedDuck = duckService.patchMotherToNull(id);
            return new ResponseEntity<>(patchedDuck, HttpStatus.OK);
        }
        catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete Duck by Id", description = "Removes a duck by its id.")
    public ResponseEntity<Duck> delete(@PathVariable("id") Long id) {
        try {
            Duck duckDeleted = duckService.deleteById(id);

            return new ResponseEntity<>(duckDeleted, HttpStatus.OK);
        }
        catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
        }
    }
}
