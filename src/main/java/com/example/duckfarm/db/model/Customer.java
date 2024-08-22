package com.example.duckfarm.db.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer")
public class Customer {

    @Schema(example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Schema(example = "Luiz Victor", required = true)
    @Column
    private String name;

    @Schema(example = "luizexample@email.com", required = true)
    @Column
    private String email;

    @Schema(example = "luiz@password", required = true)
    @Column
    private String password;

    @Schema(example = "0", required = true)
    @Column
    private Integer has_discount;
}
