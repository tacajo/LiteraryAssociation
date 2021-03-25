package com.LiteraryAssociation.service;

import com.LiteraryAssociation.model.Merchant;
import com.LiteraryAssociation.model.PaymentWay;

import java.util.List;

public interface MerchantService {

    Merchant save(Merchant merchant);

    Merchant getOne();

    void registed();

    List<PaymentWay> getPaymentWays();

    void addPaymentWay(String name);

    void deletePaymentWay(String name);

    void addUuid(String uuid);
}
