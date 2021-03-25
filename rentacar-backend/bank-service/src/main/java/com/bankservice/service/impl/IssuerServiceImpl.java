package com.bankservice.service.impl;

import com.bankservice.model.Account;
import com.bankservice.model.Issuer;
import com.bankservice.model.Payment;
import com.bankservice.repository.IssuerRepository;
import com.bankservice.service.AccountService;
import com.bankservice.service.IssuerService;
import com.bankservice.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IssuerServiceImpl implements IssuerService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IssuerRepository issuerRepository;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private AccountService accountService;

    public Issuer findById(Long id) {
        return issuerRepository.findById(id).get();
    }

    public Issuer findByUsername(String username) {
        return issuerRepository.findByUsername(username);
    }

    public Issuer save(Issuer issuer) {
        return issuerRepository.save(issuer);
    }

    public boolean checkBalance(String pan, String acquirerOrderId) {
        Payment payment = paymentService.findByUuid(acquirerOrderId);
        Account issuerAccount = accountService.findByPan(pan);

        if(issuerAccount.getBalance() >= payment.getAmount()) {
            issuerAccount.setBalance(issuerAccount.getBalance() - payment.getAmount());
            accountService.save(issuerAccount);
            return true;
        }
        return false;
    }

    public void rollback(Long accountId, Long amount) {
        Account account = accountService.findById(accountId);
        account.setBalance(account.getBalance() + amount);
    }
}
