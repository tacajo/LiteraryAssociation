package com.paymentconcentrator.service.impl;


import com.paymentconcentrator.model.Payment;
import com.paymentconcentrator.repository.PaymentRepository;
import com.paymentconcentrator.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment addTransaction(Payment payment) {
        payment.setProcessed(false);
        return paymentRepository.save(payment);
    }

    @Override
    public Payment findByUuid(String uuid) {
        return paymentRepository.findByUuid(uuid);
    }

    @Scheduled(cron = "0 0 0 * * ?")
    private void clearTransaction() {
        List<Payment> payments = paymentRepository.findAll();
        LocalDate today = LocalDate.now();
        for (Payment payment:payments) {
            if(payment.getDate().isBefore(today.minusDays(15L)) && payment.getState().equals("not processed")) {
                paymentRepository.delete(payment);
            }
        }
    }
}
