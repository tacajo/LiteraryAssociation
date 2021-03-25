package com.LiteraryAssociation.handler;

import com.LiteraryAssociation.model.Book;
import com.LiteraryAssociation.repository.EditorRepository;
import com.LiteraryAssociation.service.BookService;
import com.LiteraryAssociation.service.EditorService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainEditor implements JavaDelegate {

    @Autowired
    private BookService bookService;

    @Autowired
    private EditorService editorService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        Long idBook = (Long) delegateExecution.getVariable("idKnjige");
        Book book = bookService.findOneById(idBook);

        book.setEditor(editorService.findOneById(6L));
        bookService.save(book);

        delegateExecution.setVariable("editor", editorService.findOneById(6L).getUsername());
        delegateExecution.setVariable("editorId",editorService.findOneById(6L).getId().toString());
    }
}
