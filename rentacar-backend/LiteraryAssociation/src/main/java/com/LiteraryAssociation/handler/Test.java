package com.LiteraryAssociation.handler;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class Test implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("---------------------------------------");
        System.out.println("usao test handler");
        delegateExecution.setVariable("uspesnoPlacanje", true);
        System.out.println("---------------------------------------");
        try {
            throw new Exception("Test");
        }
        catch (Exception e) {
            System.out.println("nije dobro izvrseno placanje");
            throw new BpmnError("ERROR_500","error_payment");
        }
    }
}
