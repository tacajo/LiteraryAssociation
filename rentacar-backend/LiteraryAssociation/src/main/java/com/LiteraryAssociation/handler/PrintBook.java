package com.LiteraryAssociation.handler;

import com.LiteraryAssociation.model.Book;
import com.LiteraryAssociation.service.BookService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class PrintBook implements JavaDelegate {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BookService bookService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        logger.info("usao u PrintBook");

        HashMap<String, Object> data = (HashMap<String, Object>) delegateExecution.getVariable("formValue");
        Book book = bookService.findByTitle(delegateExecution.getVariable("naslov").toString());
        book.setPrint(true);
        bookService.save(book);
    }
}
