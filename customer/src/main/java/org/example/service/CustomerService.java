package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.Customer;
import org.example.model.CustomerRegisterRequest;
import org.example.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    public void registerCustomer(CustomerRegisterRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .build();
        repository.save(customer);
    }
}
