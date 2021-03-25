package com.bankservice.controller;

import com.bankservice.dto.*;
import com.bankservice.model.Account;
import com.bankservice.model.Acquirer;
import com.bankservice.repository.AcquirerRepository;
import com.bankservice.service.AcquirerService;
import com.bankservice.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ThreadLocalRandom;

@RestController
@CrossOrigin(origins = "http://localhost:4201")
@RequestMapping(value = "/acquirer")
public class AcquirerBankController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AcquirerService acquirerService;

    @Autowired
    private AcquirerRepository acquirerRepository;

    @Autowired
    private PaymentService paymentService;

    @GetMapping(value = "/{uuid}")
    private MerchantDataDTO getData(@PathVariable String uuid) {
        logger.info("getting data for merchant: password and id");
        MerchantDataDTO merchantDataDTO = new MerchantDataDTO();
        Acquirer acquirer = acquirerService.addAcquirer(uuid);

        merchantDataDTO.setMerchant_id(acquirer.getMerchant_id());
        merchantDataDTO.setMerchant_password(acquirer.getMerchant_password());

        return merchantDataDTO;
    }

    @PostMapping(value = "/{uuid}")
    public ResponseEntity<PaymentDataDTO> getPaymentData(@RequestBody RequestDTO requestDTO, @PathVariable String uuid) {
        logger.info("getting payment data...");
        PaymentDataDTO paymentDataDTO = new PaymentDataDTO();
        Acquirer acquirer = acquirerRepository.findByLu(uuid);
        if (requestDTO.getMerchantid().equals(acquirer.getMerchant_id()) &&
                requestDTO.getMerchant_password().equals(acquirer.getMerchant_password())) {
            logger.info("merchant id and password is correct..");
            paymentDataDTO.setPayment_id(ThreadLocalRandom.current().nextInt(1, 10000000 + 1));
            paymentDataDTO.setPayment_url("/bank");
            System.out.println(paymentDataDTO);
            return new ResponseEntity<>(paymentDataDTO, HttpStatus.OK);
        } else {
            logger.error("invalid merchant id and password");
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }


    @PostMapping(value = "/check/{uuid}")
    public ResultTransactionDTO checkBanks(@PathVariable String uuid, @RequestBody AccountDTO accountDTO) {
        logger.info("checking issuer bank and acquirer bank...");
        System.out.println(accountDTO);
        Account account = new Account();
        account.setDate(accountDTO.getDate());
        account.setCardHolderName(accountDTO.getCardHolderName());
        account.setPan(accountDTO.getPan());
        account.setSecurityCode(accountDTO.getSecurityCode());

        ResultTransactionDTO dto = new ResultTransactionDTO();
        dto.setSuccess(acquirerService.checkBank(uuid, account));
        dto.setUrl(acquirerService.getUrl(uuid, dto.isSuccess()));

        System.out.println(dto);
        return dto;

    }
}
