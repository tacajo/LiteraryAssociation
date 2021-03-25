package com.LiteraryAssociation.repository;

import com.LiteraryAssociation.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Transaction findByUuid(String uuid);
}
