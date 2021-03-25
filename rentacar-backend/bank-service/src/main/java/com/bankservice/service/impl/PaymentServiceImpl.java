package com.bankservice.service.impl;

import com.bankservice.model.Payment;
import com.bankservice.repository.PaymentRepository;
import com.bankservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment findByUuid(String uuid) {
        return paymentRepository.findByUuid(uuid);
    }

    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }
}
