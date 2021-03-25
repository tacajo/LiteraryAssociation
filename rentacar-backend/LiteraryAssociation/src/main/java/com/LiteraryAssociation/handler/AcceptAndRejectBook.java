package com.LiteraryAssociation.handler;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AcceptAndRejectBook implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        HashMap<String, Object> data = (HashMap<String, Object>) delegateExecution.getVariable("formValue");
        if(data.get("prihvataSe").equals("true")){
            delegateExecution.setVariable("prihvacenRukopis", true);
        }else{
            delegateExecution.setVariable("prihvacenRukopis", false);
        }

    }
}
