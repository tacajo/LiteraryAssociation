package com.LiteraryAssociation.handler;

import com.LiteraryAssociation.model.Book;
import com.LiteraryAssociation.model.Editor;
import com.LiteraryAssociation.service.BookService;
import com.LiteraryAssociation.service.EditorService;
import com.LiteraryAssociation.service.EmailService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendEmailToEditor implements JavaDelegate {

    @Autowired
    private BookService bookService;

    @Autowired
    private EditorService editorService;

    @Autowired
    private EmailService emailService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        Long idBook = (Long) delegateExecution.getVariable("idKnjige");
        String username = (String) delegateExecution.getVariable("editor");

        Editor editor = editorService.findByUsername(username);
        Book book = bookService.findOneById(idBook);

        emailService.sendEmailToEditor(editor, book);

    }
}
