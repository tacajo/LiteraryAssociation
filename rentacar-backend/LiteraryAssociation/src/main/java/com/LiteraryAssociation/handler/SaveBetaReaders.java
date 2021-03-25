package com.LiteraryAssociation.handler;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class SaveBetaReaders implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        HashMap<String, Object> betaReadersForm = (HashMap<String, Object>) delegateExecution.getVariable("formValue");
        delegateExecution.setVariable("betaReaders", betaReadersForm.get("betaReaders"));
    }
}
