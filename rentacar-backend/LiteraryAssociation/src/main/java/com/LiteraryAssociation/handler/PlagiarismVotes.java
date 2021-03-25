package com.LiteraryAssociation.handler;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlagiarismVotes implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<String> committeeMembers = (List<String>) delegateExecution.getVariable("committeeMembers");
        int plagiarism = 0;
        int notPlagiarism = 0;
        for (String s:committeeMembers) {
            if(delegateExecution.getVariable(s).equals("true")) {
                plagiarism++;
            } else {
                notPlagiarism++;
            }
        }

        delegateExecution.setVariable("opetSeGlasa", false);
        if(plagiarism == committeeMembers.size()) {
            delegateExecution.setVariable("plagiarism", true);
        } else if(notPlagiarism == committeeMembers.size()) {
            delegateExecution.setVariable("plagiarism", false);
        } else {
            delegateExecution.setVariable("opetSeGlasa", true);
        }
    }
}
