package com.LiteraryAssociation.repository;

import com.LiteraryAssociation.model.PaymentWay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentWayRepository extends JpaRepository<PaymentWay, Long> {
    PaymentWay findByName(String name);
}
