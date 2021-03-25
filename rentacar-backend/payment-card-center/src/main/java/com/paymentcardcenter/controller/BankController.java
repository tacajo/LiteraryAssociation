package com.paymentcardcenter.controller;

import com.paymentcardcenter.dto.AccountDTO;
import com.paymentcardcenter.dto.IssuerResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/pcc")
public class BankController {

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping(value = "/{orderId}/{timestamp}")
    public IssuerResponseDTO check(@PathVariable("orderId") String orderId, @PathVariable("timestamp") String timestamp,
                      @RequestBody AccountDTO accountDTO) {
        System.out.println(orderId);
        System.out.println(timestamp);
        System.out.println(accountDTO);
        IssuerResponseDTO issuerResponseDTO = new IssuerResponseDTO();


        if(orderId != null && timestamp != null && accountDTO != null) {

            String url = "http://localhost:8083/issuer-bank/" + orderId + "/" + timestamp;
            ResponseEntity<IssuerResponseDTO> responseEntity = restTemplate.postForEntity(url,
                    accountDTO, IssuerResponseDTO.class);
            issuerResponseDTO = responseEntity.getBody();

            return issuerResponseDTO;

        } else {
            issuerResponseDTO.setAcquirerOrderId(orderId);
            issuerResponseDTO.setAcquirerTimestamp(timestamp);
            issuerResponseDTO.setAuth("not valid");
            issuerResponseDTO.setState("failed");
            issuerResponseDTO.setProcessed(false);

            return issuerResponseDTO;
        }


    }

    @GetMapping(value = "/rollback/{accountId}/{amount}")
    public String rollback(@PathVariable("accountId") Long accountId, @PathVariable("amount") Long amount) {
        String url = "http://localhost:8083/issuer-bank/rollback/" + accountId + "/" + amount;
        HttpHeaders headers = new HttpHeaders();

        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
                entity, String.class);

        return responseEntity.getBody();

    }
}
