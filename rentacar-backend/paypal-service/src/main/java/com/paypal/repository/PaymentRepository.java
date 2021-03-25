package com.paypal.repository;

import com.paypal.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findById(Long id);
  
    Payment findByUuid(String uuid);

}

