package com.bitcoinservice.service.impl;

import com.bitcoinservice.dto.BitcoinDTO;
import com.bitcoinservice.dto.BitcoinResponseDTO;
import com.bitcoinservice.dto.ResponseDTO;
import com.bitcoinservice.model.Payment;
import com.bitcoinservice.repository.PaymentRepository;
import com.bitcoinservice.service.BitcoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class BitcoinServiceImpl implements BitcoinService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Value("${bitcoin.token}")
    private String bitcoinToken;

    @Override
    public ResponseEntity<?> pay(String amount, String uuid) {

        Payment paymentDB = paymentRepository.findByUuid(uuid);
        paymentDB.setCurrency("BTC");
        paymentDB.setState("pending");
        paymentDB.setTotal(amount);
        paymentDB = paymentRepository.save(paymentDB);

        BitcoinDTO bitcoinDTO = new BitcoinDTO();
        bitcoinDTO.setTitle("Book purchase");
        bitcoinDTO.setPrice_amount(Double.parseDouble(amount));
        bitcoinDTO.setPrice_currency("BTC");
        bitcoinDTO.setReceive_currency("BTC");
        bitcoinDTO.setSuccess_url("http://localhost:4200/success?id=" + paymentDB.getId() + "&way=bitcoin");
        bitcoinDTO.setCancel_url("http://localhost:4200/failed?id=" + paymentDB.getId()+ "&way=bitcoin");
        bitcoinDTO.setCallback_url("http://localhost:4200/failed?id=" + paymentDB.getId()+ "&way=bitcoin");

        HttpHeaders headers = new HttpHeaders();
        StringBuilder stringToken = new StringBuilder();
        stringToken.append("Bearer ").append(bitcoinToken);
        headers.add("Authorization", stringToken.toString());
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);

        HttpEntity request = new HttpEntity(bitcoinDTO, headers);
        RestTemplate rt = new RestTemplate();

        rt.setMessageConverters(messageConverters);

        ResponseEntity<BitcoinResponseDTO> response = rt.exchange("https://api-sandbox.coingate.com/v2/orders", HttpMethod.POST, request, BitcoinResponseDTO.class);
        if(response.getStatusCode().equals(HttpStatus.OK)){
            paymentDB.setDetails(response.getBody().getCreated_at());
            paymentRepository.save(paymentDB);
            return new ResponseEntity<>(new ResponseDTO(response.getBody().getPayment_url()), HttpStatus.OK);
        }else{
            return new ResponseEntity<>("failed", HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public ResponseEntity<?> editTransaction(boolean flag, Long id) {

        Optional<Payment> paymentDB = paymentRepository.findById(id);
        if(flag){
            paymentDB.get().setState("successful");
        }else{
            paymentDB.get().setState("failed");
        }
        paymentDB.get().setProcessed(true);
        paymentRepository.save(paymentDB.get());

        return new ResponseEntity<>("ok", HttpStatus.OK);
    }


}
