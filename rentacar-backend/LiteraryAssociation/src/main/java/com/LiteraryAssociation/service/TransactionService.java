package com.LiteraryAssociation.service;

import com.LiteraryAssociation.model.Transaction;
import org.springframework.http.ResponseEntity;

public interface TransactionService {

    Transaction addTransaction(Double amount);

    ResponseEntity<?> editTransaction(boolean flag, Long id);

    Transaction findByUuid(String uuid);
}
