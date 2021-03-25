package com.paymentconcentrator.service.impl;

import com.paymentconcentrator.model.Merchant;
import com.paymentconcentrator.model.Payment;
import com.paymentconcentrator.model.PaymentWay;
import com.paymentconcentrator.repository.MerchantRepository;
import com.paymentconcentrator.repository.PaymentWayRepository;
import com.paymentconcentrator.service.MerchantService;
import com.paymentconcentrator.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private PaymentWayRepository paymentWayRepository;

    @Autowired
    private PaymentService paymentService;

    public Merchant save(Merchant merchant) {
        return merchantRepository.save(merchant);
    }

    public Merchant getOne() {
        return merchantRepository.findById(1L).get();
    }

    public Merchant register(Merchant merchant) {
        return merchantRepository.save(merchant);
    }


    public List<PaymentWay> getPaymentWays() {
        return paymentWayRepository.findAll();
    }

    public void addPaymentWay(String name, String uuid) {
        PaymentWay paymentWayDB = paymentWayRepository.findByName(name);
        Merchant merchant = merchantRepository.findByLu(uuid);
        merchant.getPaymentWays().add(paymentWayDB);
        merchantRepository.save(merchant);
    }

    public void deletePaymentWay(String name, String uuid) {
        PaymentWay paymentWayDB = paymentWayRepository.findByName(name);
        if(paymentWayDB != null) {
            Merchant merchant = merchantRepository.findByLu(uuid);
            merchant.getPaymentWays().remove(paymentWayDB);
            merchantRepository.save(merchant);
           // paymentWayRepository.delete(paymentWayDB);
        }
    }

    @Override
    public List<PaymentWay> merchantPaymentWays(String uuid) {
        Payment payment = paymentService.findByUuid(uuid);
        Merchant merchant = merchantRepository.findByLu(payment.getLuUuid());
        return merchant.getPaymentWays();
    }
}
