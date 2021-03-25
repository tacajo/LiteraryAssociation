package com.paymentconcentrator.service;


import com.paymentconcentrator.model.Merchant;
import com.paymentconcentrator.model.PaymentWay;

import java.util.List;

public interface MerchantService {

    Merchant save(Merchant merchant);

    Merchant getOne();

    Merchant register(Merchant merchant);

    List<PaymentWay> getPaymentWays();

    void addPaymentWay(String name, String uuid);

    void deletePaymentWay(String name, String uuid);

    List<PaymentWay> merchantPaymentWays(String uuid);
}
