package com.LiteraryAssociation.handler;

import com.LiteraryAssociation.model.Book;
import com.LiteraryAssociation.service.BookService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class PlagiarismDetection implements JavaDelegate {

    @Autowired
    private BookService bookService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

//        List<Book> booksDB = bookService.findAll();
//        HashMap<String, Object> books = new HashMap<>();
//        for(Book b : booksDB){
//            books.put(b.getId().toString(), b.getTitle());
//        }
//        delegateExecution.setVariable("booksForPlagiarism", books);
//
//        List<FormField> properties = new ArrayList<>();
//        EnumFormType enumType = new EnumFormType();

    }

}
