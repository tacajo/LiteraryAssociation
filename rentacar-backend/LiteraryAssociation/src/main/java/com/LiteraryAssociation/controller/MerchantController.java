package com.LiteraryAssociation.controller;

import com.LiteraryAssociation.dto.MerchantDTO;
import com.LiteraryAssociation.model.Merchant;
import com.LiteraryAssociation.model.PaymentWay;
import com.LiteraryAssociation.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "merchant")
@CrossOrigin(origins = "https://localhost:4200")
public class MerchantController {

    @Autowired
    private MerchantService merchantService;

    @GetMapping
    public MerchantDTO getMerchant() {
        Merchant merchant = merchantService.getOne();
        MerchantDTO dto = new MerchantDTO();
        dto.setRegistered(merchant.isRegistered());
        dto.setId(merchant.getId());
        for (PaymentWay paymentWay:merchant.getPaymentWays()){
            dto.getPaymentWays().add(paymentWay.getName());
        }
        dto.setUuid(merchant.getUuid());
        return dto;
    }

    @PutMapping
    public void registred() {
        merchantService.registed();
    }

    @PutMapping(value = "/payment-way")
    public void addPaymentWay(@RequestBody String name) {
        merchantService.addPaymentWay(name);
    }

    @PutMapping(value = "/delete-payment-way")
    public void deletePaymentWay(@RequestBody String name) {
        System.out.println("usao");
        merchantService.deletePaymentWay(name);
    }

    @GetMapping(value = "/payment-way")
    public List<PaymentWay> getPaymentWay() {
        return merchantService.getPaymentWays();
    }

    @PutMapping(value = "/uuid")
    public void addUuid(@RequestBody String uuid) {
        merchantService.addUuid(uuid);
    }

}
