package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.Customer;
import org.example.model.CustomerRegisterRequest;
import org.example.model.FraudCheckResponse;
import org.example.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final RestTemplate restTemplate;
    public void registerCustomer(CustomerRegisterRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .build();
        repository.saveAndFlush(customer);


        FraudCheckResponse response = restTemplate.getForObject(
                "http://localhost:8081/api/fraud/{customerId}",
                FraudCheckResponse.class,
                customer.getId()
        );

        if(response.getIsFraudster()) throw new IllegalStateException("fraudster");
    }
}
