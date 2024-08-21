package com.example.duckfarm.controllers;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("duck")
@Tag(name = "Duck Controller", description = "Endpoints to make operations about ducks.")
public class DuckController {
    @GetMapping
    @Operation(summary = "Get All Ducks", description = "Returns a list of ducks registered.")
    public ArrayList<String> getAllDucks() {
        ArrayList<String> ducks = new ArrayList<>();
        return ducks;
    }
}
