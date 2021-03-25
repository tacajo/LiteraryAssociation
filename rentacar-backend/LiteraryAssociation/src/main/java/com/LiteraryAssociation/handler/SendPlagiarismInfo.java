package com.LiteraryAssociation.handler;

import com.LiteraryAssociation.service.EditorService;
import com.LiteraryAssociation.service.EmailService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendPlagiarismInfo implements JavaDelegate {

    @Autowired
    private EmailService emailService;

    @Autowired
    private EditorService editorService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        if (delegateExecution.getVariable("plagiarism").equals("true")) {
            emailService.sendMessage("book is plagiarism", editorService.findByUsername("ana"));
        } else {
            emailService.sendMessage("book is not plagiarism", editorService.findByUsername("ana"));

        }
    }
}
