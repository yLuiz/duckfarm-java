package com.example.duckfarm.http.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.duckfarm.db.model.Duck;
import com.example.duckfarm.db.repositories.DuckRepository;
import com.example.duckfarm.shared.dto.input.CreateDuckDTO;

@Service
public class DuckService {

    @Autowired
    private DuckRepository duckRepository;

    public Duck create(CreateDuckDTO payload) {
        Duck duck = new Duck(payload);

        if (payload.getMother_id() == null) {
            return duckRepository.save(duck);
        }

        Optional<Duck> duckMother = duckRepository.findById(payload.getMother_id());

        if (duckMother.orElse(null) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mãe pata não foi encontrada.");
        }

        duck.setMother(duckMother.get());

        return duckRepository.save(duck);
    }

    public Duck findById(Long id) {
        Optional<Duck> duckExists = duckRepository.findById(id);
        return duckExists.orElse(null);
    }

    public List<Duck> getAll() {
        return duckRepository.findAll();
    }

    public Duck deleteById(Long id) {
        Optional<Duck> duckExists = duckRepository.findById(id);

        if (duckExists.orElse(null) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pato não foi encontrada.");
        }
        
        if (!duckExists.get().getChildren().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pato não pode ser deletado, pois possui filhos associados.");
        }

        duckRepository.deleteById(id);
        return duckExists.get();
    }

}
