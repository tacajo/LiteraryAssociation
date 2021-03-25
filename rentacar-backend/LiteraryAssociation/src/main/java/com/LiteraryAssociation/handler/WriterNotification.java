package com.LiteraryAssociation.handler;

import com.LiteraryAssociation.model.User;
import com.LiteraryAssociation.service.EmailService;
import com.LiteraryAssociation.service.WriterService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WriterNotification implements JavaDelegate {

    @Autowired
    private EmailService emailService;

    @Autowired
    private WriterService writerService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String message = (String) delegateExecution.getVariable("message");
        System.out.println(message);

        emailService.sendMessage(message,
                (User) writerService.findById((Long) delegateExecution.getVariable("writerId")));
    }
}
