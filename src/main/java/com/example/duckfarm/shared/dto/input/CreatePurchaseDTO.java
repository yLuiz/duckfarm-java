package com.example.duckfarm.shared.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePurchaseDTO {
    @Schema(example = "1", required = true)
    private Long duck_id;

    @Schema(example = "1", required = true)
    private Long customer_id;
}
