package com.example.duckfarm.http.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.duckfarm.db.model.Duck;
import com.example.duckfarm.http.services.DuckService;
import com.example.duckfarm.shared.dto.input.CreateDuckDTO;
import com.example.duckfarm.shared.dto.output.DuckResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("duck")
@Tag(name = "Duck Controller", description = "Endpoints to make operations about ducks.")
public class DuckController {


    @Autowired
    private DuckService duckService;

    @PostMapping
    @Operation(summary = "Create Duck", description = "Creates a new duck.")
    public ResponseEntity<Duck> create(
        @RequestBody CreateDuckDTO body
    ) {

        Duck duck = duckService.create(body);
        return new ResponseEntity<>(duck, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get All Ducks", description = "Returns a list of ducks registered.")
    public List<Duck> getAll() {
        List<Duck> ducks = duckService.getAll();
        return ducks;
    }

    @GetMapping("{id}")
    @Operation(summary = "Get One Duck by Id", description = "Returns a list of ducks registered.")
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
