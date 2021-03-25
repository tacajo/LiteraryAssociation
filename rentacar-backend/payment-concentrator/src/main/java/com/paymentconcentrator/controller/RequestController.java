package com.paymentconcentrator.controller;


import com.paymentconcentrator.dto.PaymentDataDTO;
import com.paymentconcentrator.model.Merchant;
import com.paymentconcentrator.model.Payment;
import com.paymentconcentrator.model.Request;
import com.paymentconcentrator.repository.MerchantRepository;
import com.paymentconcentrator.repository.PaymentRepository;
import com.paymentconcentrator.repository.RequestRepository;
import com.paymentconcentrator.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4201"})
@RequestMapping(value = "/request")
public class RequestController {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private RequestService requestService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/{uuid}")
    public ResponseEntity<?> addRequest(@PathVariable String uuid) {
        Request request = new Request();
        Payment payment = paymentRepository.findByUuid(uuid);
        Merchant merchant = merchantRepository.findByLu(payment.getLuUuid());


        request.setAmount(payment.getAmount());
        request.setMerchantid(merchant.getMerchantid());
        request.setMerchant_password(merchant.getMerchant_password());
        request.setMerchantOrderId(uuid);
        request.setMerchantTimestamp(null);
        request.setSuccess_url("http://localhost:4200/success");
        request.setFailed_url("http://localhost:4200/failed");
        request.setError_url("http://localhost:4200/failed");
        request = requestRepository.save(request);

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<PaymentDataDTO> responseEntity = restTemplate.postForEntity("http://localhost:8083/acquirer/" + merchant.getLu(),
                request, PaymentDataDTO.class);

        System.out.println(responseEntity);
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return new ResponseEntity<PaymentDataDTO>(responseEntity.getBody(), HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("ERROR", HttpStatus.BAD_REQUEST);
        }


    }

    @GetMapping(value = "/{orderId}/{success}")
    public String getUrl(@PathVariable("orderId")String orderId, @PathVariable("success") boolean success) {
        System.out.println("usao");
        System.out.println(requestService.findUrl(orderId, success));
        return requestService.findUrl(orderId, success);
    }
}
