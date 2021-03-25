package com.LiteraryAssociation.handler;

import com.LiteraryAssociation.model.Editor;
import com.LiteraryAssociation.model.Notes;
import com.LiteraryAssociation.model.User;
import com.LiteraryAssociation.service.NotesService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AddNotes implements JavaDelegate {

    @Autowired
    private NotesService notesService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HashMap<String, Object> data = (HashMap<String, Object>) delegateExecution.getVariable("formValue");
        String notes = (String) data.get("notes").toString();

        Notes notesDB = new Notes();
        notesDB.setNotes(notes);
        notesDB.setEditor((Editor) user);
        notesService.save(notesDB);

    }
}
