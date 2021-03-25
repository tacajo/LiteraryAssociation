package com.LiteraryAssociation.handler;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class NeedMoreChanges implements JavaDelegate {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void execute(DelegateExecution delegateExecution) throws Exception {
        logger.info("usao u NeedMoreChanges");

        HashMap<String, Object> data = (HashMap<String, Object>) delegateExecution.getVariable("formValue");
        if(data.get("izmene").equals("true"))
            delegateExecution.setVariable("potrebeIzmene", true);
        else
            delegateExecution.setVariable("potrebeIzmene", false);

    }
}
