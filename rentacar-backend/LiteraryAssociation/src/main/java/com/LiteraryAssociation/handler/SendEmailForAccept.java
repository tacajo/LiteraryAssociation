package com.LiteraryAssociation.handler;

import com.LiteraryAssociation.model.Book;
import com.LiteraryAssociation.model.Editor;
import com.LiteraryAssociation.model.Writer;
import com.LiteraryAssociation.service.BookService;
import com.LiteraryAssociation.service.EmailService;
import com.LiteraryAssociation.service.WriterService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendEmailForAccept implements JavaDelegate {

    @Autowired
    private BookService bookService;

    @Autowired
    private WriterService writerService;

    @Autowired
    private EmailService emailService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        System.out.println("usao u SendEmailForAccept");

        Long idBook = (Long) delegateExecution.getVariable("idKnjige");
        Book book = bookService.findOneById(idBook);

        Writer writer = writerService.findById(book.getWriter().getId());
        emailService.sendEmailForAccept(writer, book);

    }
}
