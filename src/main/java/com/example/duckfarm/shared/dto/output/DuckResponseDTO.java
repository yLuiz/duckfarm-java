package com.example.duckfarm.shared.dto.output;

import java.util.HashSet;
import java.util.Set;

import com.example.duckfarm.db.model.Duck;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public final class DuckResponseDTO extends Duck {

    public DuckResponseDTO(Duck duck) {
        setId(duck.getId());
        setName(duck.getName());
        setPrice(duck.getPrice());
        setDuck_children(duck.getChildren().isEmpty() ? null : duck.getChildren());
        setMotherWithoutCustomer(duck.getMother());
        setCustomer(duck.getCustomer());
    }

    public void setMotherWithoutCustomer(Duck mother) {
        if (mother == null) {
            setDuck_mother(mother);
            return;
        }

        Duck currentMother = new Duck(mother);

        currentMother.setCustomer(null);
        setDuck_mother(currentMother);
    }

    private Duck duck_mother = null;
    private Set<Duck> duck_children = new HashSet<>();
}
