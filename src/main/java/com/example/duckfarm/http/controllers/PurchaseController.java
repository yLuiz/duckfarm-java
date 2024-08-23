package com.example.duckfarm.http.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.duckfarm.db.model.Purchase;
import com.example.duckfarm.http.services.PurchaseService;
import com.example.duckfarm.shared.dto.input.CreatePurchaseDTO;
import com.example.duckfarm.shared.dto.output.PurchaseResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Validated
@RestController
@RequestMapping("purchase")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Purchase Controller", description = "Endpoints to make operations about purchases.")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @PostMapping()
    @Operation(summary = "Create Purchase", description = "Creates a new purchase.")
    public ResponseEntity<PurchaseResponseDTO> create(@RequestBody CreatePurchaseDTO payload) {
        return new ResponseEntity<>(purchaseService.create(payload), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    @Operation(summary = "Get One Purchase by Id", description = "Returns a purchase by id.")
    public ResponseEntity<PurchaseResponseDTO> getById(@PathVariable("id") Long id) {

        try {
            Purchase purchase = purchaseService.findById(id);

            return new ResponseEntity<>(new PurchaseResponseDTO(purchase), HttpStatus.OK);
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping()
    @Operation(summary = "Get All Purchases", description = "Returns a list of purchases registered.")
    public Page<PurchaseResponseDTO> getAll(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size
    ) {

        if (page == null || size == null) {
            page = 0;
            size = 10;
        }

        Page<Purchase> purchases = purchaseService.findAll(page, size);
        Page<PurchaseResponseDTO> purchasesResponse = purchases.map(PurchaseResponseDTO::new);
        return purchasesResponse;
    }
}
