package com.paymentconcentrator.controller;

import com.paymentconcentrator.dto.TransactionDTO;
import com.paymentconcentrator.model.Payment;
import com.paymentconcentrator.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/transaction")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public void addTransaction(@RequestBody TransactionDTO transactionDTO) {
        Payment payment = new Payment();
        payment.setAmount(transactionDTO.getAmount());
        payment.setLuport(transactionDTO.getLuport());
        payment.setUuid(transactionDTO.getUuid());
        payment.setCurrency("USD");
        payment.setTotal(transactionDTO.getAmount().toString());
        payment.setState("not processed");
        payment.setLuUuid(transactionDTO.getLuUuid());
        payment.setUsername(transactionDTO.getUsername());
        payment.setDate(LocalDate.now());
        paymentService.addTransaction(payment);
        System.out.println("uradjeno");

    }

    @GetMapping(value = "/{amount}")
    public String pay(@PathVariable Long amount) {
        System.out.println("usao u sistem");
        System.out.println(amount);
        if (amount < 300L) {
            return "successful";
        } else {
            return "failed";
        }
    }
}
