package com.bankservice.controller;

import com.bankservice.dto.AccountDTO;
import com.bankservice.dto.IssuerResponseDTO;
import com.bankservice.service.IssuerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4201")
@RequestMapping(value = "/issuer-bank")
public class IssuerBankController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IssuerService issuerService;

    @PostMapping(value = "/{orderId}/{timestamp}")
    public IssuerResponseDTO check(@PathVariable("orderId") String orderId, @PathVariable("timestamp") String timestamp,
                                   @RequestBody AccountDTO accountDTO) {

        IssuerResponseDTO issuerResponseDTO = new IssuerResponseDTO();
        issuerResponseDTO.setAcquirerOrderId(orderId);
        issuerResponseDTO.setAcquirerTimestamp(timestamp);
        issuerResponseDTO.setIssuerOrderId(UUID.randomUUID().toString());
        issuerResponseDTO.setIssuerTimestamp((new Timestamp(System.currentTimeMillis()).toString()));
        issuerResponseDTO.setProcessed(issuerService.checkBalance(accountDTO.getPan(), orderId));
        if (issuerResponseDTO.isProcessed()) {
            issuerResponseDTO.setAuth("valid");
            issuerResponseDTO.setState("successful");
            logger.info(String.format("Successful payment with id: %s", orderId));
        } else {
            issuerResponseDTO.setAuth("not valid");
            issuerResponseDTO.setState("failed");
            logger.error(String.format("Failed payment with id: %s", orderId));
        }

        return issuerResponseDTO;
    }

    @GetMapping(value = "/rollback/{accountId}/{amount}")
    public String rollback(@PathVariable("accountId") Long accountId, @PathVariable("amount") Long amount) {
        logger.warn(String.format("rolling back data because payment is failed... user id : %s", accountId));
        try {
            issuerService.rollback(accountId, amount);
            return "successful";
        } catch (Exception e) {
            return "error";
        }
    }
}
