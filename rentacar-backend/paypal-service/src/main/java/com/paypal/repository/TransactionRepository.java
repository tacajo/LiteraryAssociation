package com.paypal.repository;


import com.paypal.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Transaction findByUuid(String uuid);
}
