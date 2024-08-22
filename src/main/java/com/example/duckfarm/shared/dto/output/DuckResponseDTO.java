package com.example.duckfarm.shared.dto.output;

import com.example.duckfarm.db.model.Duck;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DuckResponseDTO extends Duck {

    public DuckResponseDTO(Duck duck) {
        setId(duck.getId());
        setName(duck.getName());
        setPrice(duck.getPrice());
        setChildren(duck.getChildren());
        mother_id = duck.getMother() != null ? duck.getMother().getId() : null;
    }

    private Long mother_id = null;
}
