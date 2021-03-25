package com.bankservice.service;

import com.bankservice.model.Issuer;

public interface IssuerService {

    Issuer findById(Long id);

    Issuer findByUsername(String username);

    Issuer save(Issuer issuer);

    boolean checkBalance(String pan, String acquirerOrderId);

    void rollback(Long accountId, Long amount);
}
