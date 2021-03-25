package com.LiteraryAssociation.handler;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class BetaReaderCheck implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        HashMap<String, Object> registrationData = (HashMap<String, Object>) delegateExecution.getVariable("formValue");
        delegateExecution.setVariable("reader", registrationData);
        if(registrationData.get("betaReader").equals("true")) {
            delegateExecution.setVariable("betaReader", true);
        } else {
            delegateExecution.setVariable("betaReader", false);
        }
    }
}
