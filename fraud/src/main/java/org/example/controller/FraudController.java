package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.FraudCheckResponse;
import org.example.service.FraudService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/fraud")
public class FraudController {
    private final FraudService fraudService;


    @GetMapping("/{customerId}")
    public FraudCheckResponse isFraudster(@PathVariable Integer customerId) {
        boolean isFraudulentCustomer = fraudService.isFraudelentCuctomer(customerId);
        log.info("fraud check request for costumer {}", customerId);
        return new FraudCheckResponse(isFraudulentCustomer);

    }
}
