package com.example.duckfarm.http.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.duckfarm.db.model.Customer;
import com.example.duckfarm.db.model.Duck;
import com.example.duckfarm.db.repositories.DuckRepository;
import com.example.duckfarm.shared.dto.input.CreateDuckDTO;

@Service
public class DuckService {

    @Autowired
    private DuckRepository duckRepository;

    @Autowired
    private CustomerService customerService;

    public Duck create(CreateDuckDTO payload) {
        Duck duck = new Duck(payload);

        try {
            Customer customer = customerService.findById(payload.getCustomer_id());

            duck.setCustomer(customer);

            if (payload.getMother_id() == null) {
                return duckRepository.save(duck);
            }

            Optional<Duck> duckMother = duckRepository.findById(payload.getMother_id());

            if (duckMother.orElse(null) == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Mãe pata não foi encontrada.");
            }

            duck.setMother(duckMother.get());

            return duckRepository.save(duck);
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public Duck findById(Long id) {
        Optional<Duck> duckExists = duckRepository.findById(id);
        return duckExists.orElse(null);
    }

    public Page<Duck> getAll(int page, int size) {

        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.DESC,
                "id");

        Page<Duck> ducks = duckRepository.findAllPage(pageRequest);

        return ducks;
    }

    public Duck deleteById(Long id) {
        Optional<Duck> duckExists = duckRepository.findById(id);

        if (duckExists.orElse(null) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pato não foi encontrado.");
        }

        if (!duckExists.get().getChildren().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pato não pode ser deletado, pois possui filhos associados.");
        }

        duckRepository.deleteById(id);
        return duckExists.get();
    }

}
