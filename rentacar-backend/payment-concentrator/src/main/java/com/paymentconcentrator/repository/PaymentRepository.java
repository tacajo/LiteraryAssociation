package com.paymentconcentrator.repository;

import com.paymentconcentrator.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByUuid(String uuid);
}
