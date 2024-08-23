package com.example.duckfarm.shared.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateDuckDTO {

    @Schema(example = "Patolino", required = true)
    @NotNull(message = "Campo nome não pode ser nulo.")
    @NotBlank(message = "Campo nome não pode ser vazio.")
    private String name;

    @Schema(example = "209.90", required = true)
    @NotNull(message = "Campo preço não pode ser nulo.")
    private Double price;

    @Schema(example = "1", hidden=true)
    private Long customer_id;
    
    @Schema(example = "1")
    private Long mother_id;

    
}
