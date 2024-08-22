package com.example.duckfarm.http.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    public Duck update(Long id, CreateDuckDTO payload) {
        try {
            Duck duck = findById(id);

            if (duck == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pato não foi encontrado.");
            }

            Boolean customerNotNull = payload.getCustomer_id() != null;
            Boolean sameCustomer = customerNotNull && Objects.equals(duck.getCustomer().getId(), payload.getCustomer_id());

            /* Customer */
            if (customerNotNull) {
                duck.setCustomer(sameCustomer ? duck.getCustomer() : customerService.findById(payload.getCustomer_id()));
            }

            /* Mother */
            Long motherId = duck.getMother() == null ? null : duck.getMother().getId();
            Boolean motherNotNull = payload.getMother_id() != null;
            Boolean sameMother = motherNotNull && Objects.equals(motherId, payload.getMother_id());
            Boolean isHerself = Objects.equals(duck.getId(), payload.getMother_id());

            if (isHerself) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A mãe pata não pode ser si mesmo.");
            }

            List<Long> childIds = new ArrayList<>();
            for (Duck child : duck.getChildren()) {
                childIds.add(child.getId());
            }

            if (motherNotNull && childIds.contains(payload.getMother_id())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A mãe pata não pode ser um de seus filhos.");
            }

            if (sameMother) {
                duck.setMother(duck.getMother());
            } else if (motherNotNull) {

                Optional<Duck> duckMother = duckRepository.findById(payload.getMother_id());
                if (duckMother.orElse(null) == null) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Mãe pata não foi encontrada.");
                }

                duck.setMother(duckMother.get());
            }

            duck.setName(payload.getName() == null ? duck.getName() : payload.getName());
            duck.setPrice(payload.getPrice() == null ? duck.getPrice() : payload.getPrice());

            return duckRepository.save(duck);

        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

    public Duck patchMotherToNull(Long id) {
        Duck duck = findById(id);

        if (duck == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pato não foi encontrado.");
        }

        duck.setMother(null);

        duckRepository.save(duck);

        return duck;
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
