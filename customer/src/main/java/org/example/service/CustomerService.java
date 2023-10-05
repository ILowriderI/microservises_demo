package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.RabbitMQMessageProducer;
import org.example.model.Customer;
import org.example.model.CustomerRegisterRequest;
import org.example.model.FraudCheckResponse;
import org.example.model.NotificationRequest;
import org.example.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final RestTemplate restTemplate;
    private final RabbitMQMessageProducer mqMessageProducer;

    public void registerCustomer(CustomerRegisterRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .build();
        repository.saveAndFlush(customer);

        FraudCheckResponse response = restTemplate.getForObject(
                "http://FRAUD/api/fraud/{customerId}",
                FraudCheckResponse.class,
                customer.getId()
        );

//       NotificationRequest notificationRequest = restTemplate.postForObject(
//                "http://NOTIFICATION/api/notification",
//                new NotificationRequest(
//                        customer.getId(),
//                        customer.getEmail(),
//                        "Yura",
//                        "Hi,welcome"
//                ),
//               NotificationRequest.class
//        );
        NotificationRequest notificationRequest = new NotificationRequest(
                customer.getId(),
                customer.getEmail(),
                "Yura",
                "Hi " + customer.getFirstName() + " , welcome"
        );
        mqMessageProducer.publish(
                notificationRequest,
                "internal.exchange",
                "internal.notification.routing-key"
        );

        if (response.getIsFraudster()) throw new IllegalStateException("fraudster");
    }
}
