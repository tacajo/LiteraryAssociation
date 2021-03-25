package com.LiteraryAssociation.handler;

import com.LiteraryAssociation.model.Book;
import com.LiteraryAssociation.model.Writer;
import com.LiteraryAssociation.service.BookService;
import com.LiteraryAssociation.service.EmailService;
import com.LiteraryAssociation.service.WriterService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendEmailForDecline implements JavaDelegate {

    @Autowired
    private EmailService emailService;

    @Autowired
    private BookService bookService;

    @Autowired
    private WriterService writerService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        System.out.println("Usao u SendEmailForDecline");
        String explanation = (String) delegateExecution.getVariable("explanation");

        Long idBook = (Long) delegateExecution.getVariable("idKnjige");
        Book book = bookService.findOneById(idBook);

        Writer writer = writerService.findById(book.getWriter().getId());
        emailService.sendEmailForDecline(writer, book, explanation);

    }
}
