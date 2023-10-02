package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.FraudCheckHistory;
import org.example.repository.FraudRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FraudService {
    private final FraudRepository fraudRepository;

    public boolean isFraudelentCuctomer(Integer customerId){
        fraudRepository.save(
                FraudCheckHistory.builder()
                        .customerId(customerId)
                        .isFraudster(false)
                        .created(LocalDateTime.now())
                        .build()
        );
        return false;
    }
}
