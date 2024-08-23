package com.example.duckfarm.shared.dto.output;

import com.example.duckfarm.db.model.Customer;
import com.example.duckfarm.db.model.Duck;
import com.example.duckfarm.db.model.Purchase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseResponseDTO extends Purchase {

    public PurchaseResponseDTO(Purchase purchase) {
        setId(purchase.getId());
        this.setPrice(purchase.getPrice());
        this.purchase_duck = purchase.getDuck();
        this.purchase_customer = purchase.getCustomer();

        this.purchase_customer.setDucks(null);
        this.purchase_customer.setPurchase(null);
        this.purchase_duck.setCustomer(null);
    }

    private Duck purchase_duck;
    private Customer purchase_customer;
}
