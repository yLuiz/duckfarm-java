package com.example.duckfarm.db.model;

import java.util.HashSet;
import java.util.Set;

import com.example.duckfarm.shared.dto.input.CreateDuckDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class Duck {

    public Duck(CreateDuckDTO createDuckDTO) {
        this.name = createDuckDTO.getName();
        this.price = createDuckDTO.getPrice();
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
    @Schema(example = "1", required = true)
    @JoinColumn(name = "mother_id")
    @JsonIgnore
    private Duck mother;

    @Schema(example = "[]", required = true)
    @OneToMany(mappedBy = "mother", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Duck> children = new HashSet<>();
}
