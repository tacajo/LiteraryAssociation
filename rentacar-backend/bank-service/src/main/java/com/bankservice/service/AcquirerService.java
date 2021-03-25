package com.bankservice.service;

import com.bankservice.model.Account;
import com.bankservice.model.Acquirer;

public interface AcquirerService {

    Acquirer save(Acquirer acquirer);

    Acquirer addAcquirer(String uuid);

    boolean checkBank(String uuid, Account account);

    String getUrl(String orderId, boolean success);
}
