package com.example.duckfarm.shared.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
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
    private String name;

    @Schema(example = "209.90", required = true)
    private Double price;

    @Schema(example = "1")
    private Long customer_id;
    
    @Schema(example = "1", required = true)
    private Long mother_id;

    
}
