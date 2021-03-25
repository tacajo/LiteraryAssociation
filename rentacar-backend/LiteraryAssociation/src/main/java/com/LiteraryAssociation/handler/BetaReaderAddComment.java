package com.LiteraryAssociation.handler;

import com.LiteraryAssociation.model.Book;
import com.LiteraryAssociation.service.BookService;
import com.LiteraryAssociation.service.EmailService;
import com.LiteraryAssociation.service.WriterService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class BetaReaderAddComment implements JavaDelegate {

    @Autowired
    private EmailService emailService;

    @Autowired
    private WriterService writerService;

    @Autowired
    private BookService bookService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        HashMap<String, Object> data = (HashMap<String, Object>) delegateExecution.getVariable("formValue");
        Book book = bookService.findByTitle(delegateExecution.getVariable("naslov").toString());
        emailService.sendMessage(data.get("komentar").toString(), book.getWriter());
    }
}
