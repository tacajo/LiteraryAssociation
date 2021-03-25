package com.bitcoinservice.repository;

import com.bitcoinservice.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findById(Long id);

    Payment findByUuid(String uuid);
}
