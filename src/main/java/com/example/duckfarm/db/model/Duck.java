package com.example.duckfarm.db.model;

import java.util.HashSet;
import java.util.Set;

import com.example.duckfarm.shared.dto.input.CreateDuckDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "duck")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
public class Duck {

    public Duck(CreateDuckDTO createDuckDTO) {
        this.name = createDuckDTO.getName();
        this.price = createDuckDTO.getPrice();
    }

    public Duck(Duck body) {
        this.id = body.getId();
        this.name = body.getName();
        this.price = body.getPrice();
        this.customer = body.getCustomer();
        this.mother = body.getMother();
        this.children = body.getChildren();
    }

    @Schema(example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(example = "Patolino", required = true)
    @Column
    private String name;

    @Schema(example = "209.90", required = true)
    @Column
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mother_id")
    @JsonIgnore
    private Duck mother;

    @Schema(example = "[]")
    @OneToMany(mappedBy = "mother", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private Set<Duck> children = new HashSet<>();

    @OneToOne(mappedBy = "duck")
    @JsonIgnore
    private Purchase purchase;

    
}
