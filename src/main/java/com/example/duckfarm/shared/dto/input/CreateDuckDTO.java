package com.example.duckfarm.shared.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateDuckDTO {

    @NotBlank(message="Nome não pode ser vazio.")
    @NotNull(message="Nome não pode ser vazio.")
    @Schema(example = "Patolino", required = true)
    private String name;

    @NotBlank(message="Preço não pode ser vazio.")
    @NotNull(message="Preço não pode ser vazio.")
    @Schema(example = "209.90", required = true)
    private Double price;

    @NotBlank(message="Cliente não pode ser vazio.")
    @NotNull(message="Cliente não pode ser vazio.")
    @Schema(example = "1", required = true)
    private Long customer_id;
    
    @Schema(example = "1", required = true)
    private Long mother_id;

    
}
