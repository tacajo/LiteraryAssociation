package com.paymentconcentrator.repository;


import com.paymentconcentrator.model.PaymentWay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentWayRepository extends JpaRepository<PaymentWay, Long> {
    PaymentWay findByName(String name);
}
