package com.eureka.repository;

import com.eureka.model.PaymentWay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentWayRepository extends JpaRepository<PaymentWay, Long> {
    PaymentWay findByName(String name);
}
