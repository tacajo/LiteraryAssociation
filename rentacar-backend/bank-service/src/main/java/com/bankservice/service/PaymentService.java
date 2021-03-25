package com.bankservice.service;

import com.bankservice.model.Payment;

public interface PaymentService {

    Payment findByUuid(String uuid);

    Payment save(Payment payment);
}
