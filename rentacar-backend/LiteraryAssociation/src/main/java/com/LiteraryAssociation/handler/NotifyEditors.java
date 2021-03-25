package com.LiteraryAssociation.handler;

import com.LiteraryAssociation.model.Editor;
import com.LiteraryAssociation.service.EditorService;
import com.LiteraryAssociation.service.EmailService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class NotifyEditors implements JavaDelegate {

    @Autowired
    private EditorService editorService;

    @Autowired
    private EmailService emailService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        HashMap<String, Object> editorsForm = (HashMap<String, Object>) delegateExecution.getVariable("formValue");

        List<String> editors = (List<String>) editorsForm.get("editors");
        delegateExecution.setVariable("editorsList", editors);
        for (String s:editors) {
            Editor editor = editorService.findOneById(Long.parseLong(s));
            emailService.sendMessage("You need to check new books reported as plagiarism", editor);
        }

    }
}
