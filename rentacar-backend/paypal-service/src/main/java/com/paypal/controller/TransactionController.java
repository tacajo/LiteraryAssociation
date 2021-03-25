package com.paypal.controller;

import com.paypal.dto.TransactionDTO;
import com.paypal.model.Transaction;
import com.paypal.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public void addTransaction(@RequestBody TransactionDTO transactionDTO) {
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setLuport(transactionDTO.getLuport());
        transaction.setUuid(transactionDTO.getUuid());
        transactionService.addTransaction(transaction);

    }
}
