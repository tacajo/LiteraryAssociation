package com.LiteraryAssociation.handler;

import com.LiteraryAssociation.model.Reader;
import com.LiteraryAssociation.service.EmailService;
import com.LiteraryAssociation.service.ReaderService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendNotification implements JavaDelegate {

    @Autowired
    private EmailService emailService;

    @Autowired
    private ReaderService readerService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Reader reader = readerService.findById((Long) delegateExecution.getVariable("betaReader"));
        emailService.sendMessage("You lost status for beta reader.", reader);
    }
}
