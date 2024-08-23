package com.example.duckfarm.db.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name = "purchase")
public class Purchase {

    public Purchase(Purchase body) {
        this.duck = body.getDuck();
        this.price = body.getPrice();
        this.customer = body.getCustomer();
    }

    @Schema(example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(example = "1", required = true)
    @JoinColumn(name = "duck_id")
    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Duck duck;

    @Schema(example = "209.90", required = true)
    @Column
    private Double price;

    @Schema(example = "1", required = true)
    @JoinColumn(name = "customer_id")
    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Customer customer;

    @Column
    private Date created_at;
}
