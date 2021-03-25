package com.paymentconcentrator.service;


import com.paymentconcentrator.model.Payment;

public interface PaymentService {

    Payment addTransaction(Payment payment);

    Payment findByUuid(String uuid);
}
