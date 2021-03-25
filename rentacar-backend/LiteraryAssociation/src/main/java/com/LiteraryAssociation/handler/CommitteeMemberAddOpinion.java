package com.LiteraryAssociation.handler;

import com.LiteraryAssociation.model.User;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class CommitteeMemberAddOpinion implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        HashMap<String, Object> data = (HashMap<String, Object>) delegateExecution.getVariable("formValue");
        delegateExecution.setVariable((String)delegateExecution.getVariable("committeeMember"), data.get("opinionCm"));
    }
}
