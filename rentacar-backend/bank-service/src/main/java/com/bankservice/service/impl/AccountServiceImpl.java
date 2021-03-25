package com.bankservice.service.impl;

import com.bankservice.model.Account;
import com.bankservice.repository.AccountRepository;
import com.bankservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account findByPan(String pan) {
        return accountRepository.findByPan(pan);
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public Account findById(Long id) {
        return accountRepository.findById(id).get();
    }
}
