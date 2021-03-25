package com.LiteraryAssociation.handler;

import com.LiteraryAssociation.model.Book;
import com.LiteraryAssociation.model.Editor;
import com.LiteraryAssociation.service.BookService;
import com.LiteraryAssociation.service.EmailService;
import com.LiteraryAssociation.service.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendingManuscript implements JavaDelegate {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        String naslov = (String) delegateExecution.getVariable("naslov");
        Book book = bookService.findByTitle(naslov);

        Editor editor = (Editor) userService.findByUsername("ana");
        emailService.sendManuscript(book, editor);
    }
}
