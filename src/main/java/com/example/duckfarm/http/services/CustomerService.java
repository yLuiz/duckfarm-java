package com.example.duckfarm.http.services;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.duckfarm.db.model.Customer;
import com.example.duckfarm.db.repositories.CustomerRepository;
import com.example.duckfarm.shared.dto.input.CreateCustomerDTO;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer create(CreateCustomerDTO payload) {

        if (payload.getHas_discount() != 0 && payload.getHas_discount() != 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Valor inválido para definir cliente com desconto.");
        }

        Customer emailAlreadyExists = customerRepository.findByEmail(payload.getEmail());

        if (emailAlreadyExists != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Este email já está em uso.");
        }

        String hashedPassword = new BCryptPasswordEncoder().encode(payload.getPassword());

        payload.setPassword(hashedPassword);
        Customer customer = new Customer(payload);

        return customerRepository.save(customer);
    }

    public Page<Customer> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.DESC,
                "id");

        return customerRepository.findAllPage(pageRequest);

    }

    public Customer findById(Long id) {

        Optional<Customer> customer = customerRepository.findById(id);

        if (!customer.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado.");
        }

        return customer.get();
    }

    public Customer  update(Long id, CreateCustomerDTO payload) {

        Integer hasDiscount = payload.getHas_discount();

        if (hasDiscount != null && (hasDiscount != 0 && hasDiscount != 1)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Valor inválido para definir cliente com desconto.");
        }

        Optional<Customer> customerExists = customerRepository.findById(id);
        if (!customerExists.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado.");
        }

        Customer emailAlreadyExists = (payload.getEmail() != null) ? customerRepository.findByEmail(payload.getEmail()) : null;

        if (emailAlreadyExists != null && !Objects.equals(emailAlreadyExists.getId(), id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Este email já está em uso.");
        }
        

        String hashedPassword = payload.getPassword() != null ? new BCryptPasswordEncoder().encode(payload.getPassword()) : customerExists.get().getPassword();
        
        Customer customerGetted = customerExists.get();
        customerGetted.setName(payload.getName() != null ? payload.getName() : customerGetted.getName());
        customerGetted.setEmail(payload.getEmail() != null ? payload.getEmail() : customerGetted.getEmail());
        customerGetted.setPassword(hashedPassword);
        customerGetted.setHas_discount(hasDiscount != null ? hasDiscount : customerGetted.getHas_discount());

        Customer customer = new Customer(customerGetted);

        return customerRepository.save(customer);
    }

    public Customer delete(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);

        if (!customer.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado.");
        }

        customerRepository.deleteById(id);

        return customer.get();
    }

}
