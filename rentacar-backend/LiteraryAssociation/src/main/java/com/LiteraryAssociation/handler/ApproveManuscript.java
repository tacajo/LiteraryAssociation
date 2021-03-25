package com.LiteraryAssociation.handler;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class ApproveManuscript implements JavaDelegate {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        HashMap<String, Object> data = (HashMap<String, Object>) delegateExecution.getVariable("formValue");
        if(data.get("odobrenje").equals("true"))
            delegateExecution.setVariable("odobreno", true);
        else
            delegateExecution.setVariable("odobreno", false);
    }
}
