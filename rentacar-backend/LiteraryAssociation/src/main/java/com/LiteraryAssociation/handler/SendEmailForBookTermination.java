package com.LiteraryAssociation.handler;

import com.LiteraryAssociation.dto.FormFieldDTO;
import com.LiteraryAssociation.model.Book;
import com.LiteraryAssociation.model.Writer;
import com.LiteraryAssociation.service.BookService;
import com.LiteraryAssociation.service.EmailService;
import com.LiteraryAssociation.service.PdfService;
import com.LiteraryAssociation.service.WriterService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SendEmailForBookTermination implements JavaDelegate {

    @Autowired
    private EmailService emailService;

    @Autowired
    private BookService bookService;

    @Autowired
    private WriterService writerService;

    @Autowired
    TaskService taskService;

    @Autowired
    private PdfService pdfService;

    @Autowired
    private RuntimeService runtimeService;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        Long idBook = (Long) delegateExecution.getVariable("idKnjige");
        Book book = bookService.findOneById(idBook);

        Writer writer = writerService.findById(book.getWriter().getId());
        emailService.sendEmailForBookTermination(writer, book);

    }
}
