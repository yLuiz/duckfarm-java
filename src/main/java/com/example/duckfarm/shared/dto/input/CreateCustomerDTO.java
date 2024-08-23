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
public class CreateCustomerDTO {

    @Schema(example = "Luiz Victor", required = true)
    private String name;

    @Schema(example = "luizexample@email.com", required = true)
    private String email;

    @Schema(example = "luiz@123", required = true)
    private String password;

    @Schema(example = "0", required = true)
    private Integer has_discount;
}
