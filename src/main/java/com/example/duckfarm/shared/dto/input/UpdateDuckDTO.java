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
public class UpdateDuckDTO {
    @Schema(example = "Patolino")
    private String name;

    @Schema(example = "209.90")
    private Double price;

    @Schema(example = "1")
    private Long customer_id;
    
    @Schema(example = "1")
    private Long mother_id;
}
