package com.LiteraryAssociation.handler;

import com.LiteraryAssociation.model.Book;
import com.LiteraryAssociation.service.BookService;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class SavePlagiarismInfo implements JavaDelegate {

    @Autowired
    private BookService bookService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        HashMap<String, Object> editorsForm = (HashMap<String, Object>) delegateExecution.getVariable("formValue");

        try {
            Book book1 = bookService.findByTitle(editorsForm.get("naziv_knjige").toString());
            Book book2 = bookService.findByTitle(editorsForm.get("naziv_original_knjige").toString());
            if (book1 == null || book2 == null ||
                    book1.getWriter().getFirstName().equals(editorsForm.get("ime_pisca").toString()) ||
                    book1.getWriter().getLastName().equals(editorsForm.get("prezime_pisca").toString()) ||
                    book2.getWriter().getFirstName().equals(editorsForm.get("ime_pisca_original_knjige").toString()) ||
                    book2.getWriter().getFirstName().equals(editorsForm.get("prezime_pisca_original_knjige").toString())) {

                throw new BpmnError("PLAGIARISM_ERROR", "plagiarism_failed");
            } else {
                delegateExecution.setVariable("plagiarismInfo", editorsForm);
            }
        } catch (Exception e) {
            throw new BpmnError("PLAGIARISM_ERROR", "plagiarism_failed");
        }


    }
}
