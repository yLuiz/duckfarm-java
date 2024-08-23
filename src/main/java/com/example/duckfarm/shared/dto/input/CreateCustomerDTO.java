package com.example.duckfarm.shared.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "Campo nome não pode ser nulo.")
    @NotBlank(message = "Campo nome não pode ser vazio.")
    private String name;

    @Schema(example = "luizexample@email.com", required = true)
    @Email(message = "Envie um email válido.")
    @NotNull(message = "Campo email não pode ser nulo.")
    @NotBlank(message = "Campo email não pode ser vazio.")
    private String email;

    @Schema(example = "luiz@123", required = true)
    @NotNull(message = "Campo senha não pode ser nulo.")
    @NotBlank(message = "Campo senha não pode ser vazio.")
    private String password;

    @Schema(example = "0", required = true)
    @NotNull(message = "Campo tem desconto não pode ser nulo.")
    private Integer has_discount;
}
