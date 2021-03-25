package com.paymentconcentrator.controller;


import com.paymentconcentrator.dto.MerchantDTO;
import com.paymentconcentrator.dto.MerchantDataDTO;
import com.paymentconcentrator.model.Merchant;
import com.paymentconcentrator.model.PaymentWay;
import com.paymentconcentrator.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "merchant")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4201"})
public class MerchantController {

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    public MerchantDTO getMerchant() {
        Merchant merchant = merchantService.getOne();
        MerchantDTO dto = new MerchantDTO();
        dto.setId(merchant.getId());
        for (PaymentWay paymentWay : merchant.getPaymentWays()) {
            dto.getPaymentWays().add(paymentWay.getName());
        }
        return dto;
    }

    @PostMapping
    public Merchant register(@RequestBody MerchantDTO dto) {
        System.out.println("usao u register");
        System.out.println(dto);
        Merchant merchant = new Merchant();
        merchant.setLuname(dto.getLuname());
        merchant.setLuport(dto.getLuport());
        merchant.setLu(UUID.randomUUID().toString());

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<MerchantDataDTO> responseEntity = restTemplate.exchange("http://localhost:8083/acquirer/" + merchant.getLu(), HttpMethod.GET,
                entity, MerchantDataDTO.class);

        MerchantDataDTO response = responseEntity.getBody();
        System.out.println(response);
        merchant.setMerchantid(response.getMerchant_id());
        merchant.setMerchant_password(response.getMerchant_password());
        return merchantService.register(merchant);
    }

    @PutMapping(value = "/payment-way/{uuid}")
    public void addPaymentWay(@RequestBody String name, @PathVariable String uuid) {
        merchantService.addPaymentWay(name, uuid);
    }

    @PutMapping(value = "/delete-payment-way/{uuid}")
    public void deletePaymentWay(@RequestBody String name, @PathVariable String uuid) {
        System.out.println("usao");
        merchantService.deletePaymentWay(name, uuid);
    }

    @GetMapping(value = "/payment-way")
    public List<PaymentWay> getPaymentWay() {
        return merchantService.getPaymentWays();
    }

    @GetMapping(value = "/payment-way/{uuid}")
    public List<PaymentWay> getMerchantPaymentWay(@PathVariable String uuid) {
        System.out.println("usao ovde");
        return merchantService.merchantPaymentWays(uuid);
    }


}
