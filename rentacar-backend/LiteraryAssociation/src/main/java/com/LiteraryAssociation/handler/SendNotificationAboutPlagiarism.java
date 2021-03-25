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
public class SendNotificationAboutPlagiarism implements JavaDelegate {

    @Autowired
    private BookService bookService;

    @Autowired
    private EditorService editorService;

    @Autowired
    private EmailService emailService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        Book book = bookService.findOneById(1L);
        delegateExecution.setVariable("idKnjige", book.getId());
        Long idBook = (Long) delegateExecution.getVariable("idKnjige");
        Editor editor = editorService.findOneById(6L);
        emailService.sendEmailToEditorAboutPlagiarism(editor, book);

    }
}
