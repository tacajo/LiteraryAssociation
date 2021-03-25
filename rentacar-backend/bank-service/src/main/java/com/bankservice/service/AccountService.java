package com.bankservice.service;

import com.bankservice.model.Account;

public interface AccountService {
    Account findByPan(String pan);

    Account save(Account account);

    Account findById(Long id);
}
