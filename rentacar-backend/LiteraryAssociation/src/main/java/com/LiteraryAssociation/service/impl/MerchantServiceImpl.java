package com.LiteraryAssociation.service.impl;

import com.LiteraryAssociation.model.Merchant;
import com.LiteraryAssociation.model.PaymentWay;
import com.LiteraryAssociation.repository.MerchantRepository;
import com.LiteraryAssociation.repository.PaymentWayRepository;
import com.LiteraryAssociation.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private PaymentWayRepository paymentWayRepository;

    public Merchant save(Merchant merchant) {
        return merchantRepository.save(merchant);
    }

    public Merchant getOne() {
        return merchantRepository.findById(1L).get();
    }

    public void registed() {
        Merchant merchant = getOne();
        merchant.setRegistered(true);
        merchantRepository.save(merchant);
    }

    public List<PaymentWay> getPaymentWays() {
        return paymentWayRepository.findAll();
    }

    public void addPaymentWay(String name) {
        PaymentWay paymentWay = new PaymentWay();
        paymentWay.setName(name);
        PaymentWay paymentWayDB = paymentWayRepository.findByName(name);
        if(paymentWayDB == null) {
            paymentWayRepository.save(paymentWay);
            Merchant merchant = getOne();
            merchant.getPaymentWays().add(paymentWay);
            merchantRepository.save(merchant);
        }
    }

    public void deletePaymentWay(String name) {
        PaymentWay paymentWayDB = paymentWayRepository.findByName(name);
        if(paymentWayDB != null) {
            Merchant merchant = getOne();
            merchant.getPaymentWays().remove(paymentWayDB);
            merchantRepository.save(merchant);
            paymentWayRepository.delete(paymentWayDB);
        }
    }

    public void addUuid(String uuid) {
        Merchant merchant = getOne();
        merchant.setUuid(uuid);
        merchantRepository.save(merchant);
    }
}
