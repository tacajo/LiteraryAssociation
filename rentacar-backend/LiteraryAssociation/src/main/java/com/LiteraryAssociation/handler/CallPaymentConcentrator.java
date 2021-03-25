package com.LiteraryAssociation.handler;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CallPaymentConcentrator implements JavaDelegate {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        String url = "http://localhost:8087/transaction/" + 200;
        System.out.println(url);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);


        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity,String.class);
            System.out.println("---------------" + responseEntity.getBody());
            if(responseEntity.getBody().equals("successful")) {
                delegateExecution.setVariable("uspesnoPlacanje", true);
                System.out.println("usao u if uspelo placanje");
            } else {
                System.out.println("usao u else throw exeption");
                throw new Exception();
            }

        }
        catch (Exception e) {
            System.out.println("nije dobro izvrseno placanje");
            throw new BpmnError("ERROR_500","error_payment");
        }
    }
}
