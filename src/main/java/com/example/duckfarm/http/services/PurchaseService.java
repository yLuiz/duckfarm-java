package com.example.duckfarm.http.services;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.duckfarm.db.model.Customer;
import com.example.duckfarm.db.model.Duck;
import com.example.duckfarm.db.model.Purchase;
import com.example.duckfarm.db.repositories.PurchaseRepository;
import com.example.duckfarm.shared.dto.input.CreateDuckDTO;
import com.example.duckfarm.shared.dto.input.CreatePurchaseDTO;
import com.example.duckfarm.shared.dto.output.PurchaseResponseDTO;
import com.example.duckfarm.shared.utils.MathRound;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private DuckService duckService;

    @Autowired
    private CustomerService customerService;

    public PurchaseResponseDTO create(CreatePurchaseDTO payload) {

        try {
            Customer customer = customerService.findById(payload.getCustomer_id());
            Duck duck = duckService.findById(payload.getDuck_id());

            Long duckCustomerId = duck.getCustomer() != null ? duck.getCustomer().getId() : null;
            if (Objects.equals(payload.getCustomer_id(), duckCustomerId)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Você não pode comprar seu próprio pato.");
            }

            Purchase purchase = new Purchase();
            purchase.setPrice(duck.getPrice());

            if (customer.getHas_discount() == 1) {
                Double discount = 20d / 100;
                Double priceWithDiscount = duck.getPrice() - (duck.getPrice() * discount);
                purchase.setPrice(MathRound.round(priceWithDiscount, 2));
            }

            purchase.setCustomer(customer);
            purchase.setDuck(duck);
            purchase.setCreated_at(new Date());
            Purchase newPurchase = purchaseRepository.save(purchase);

            if (newPurchase != null) {

                CreateDuckDTO duckDTO = new CreateDuckDTO();
                duckDTO.setCustomer_id(customer.getId());
                duckService.update(duck.getId(), duckDTO);
            }

            return new PurchaseResponseDTO(newPurchase);

        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

    public Purchase findById(Long id) {

        Optional<Purchase> purchase = purchaseRepository.findById(id);
        if (!purchase.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Compra não encontrada.");
        }

        return purchase.get();
    }

    public Page<Purchase> findAll(int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size, Sort.Direction.DESC, "id");
        return purchaseRepository.findAllPage(pageRequest);
    }

}
