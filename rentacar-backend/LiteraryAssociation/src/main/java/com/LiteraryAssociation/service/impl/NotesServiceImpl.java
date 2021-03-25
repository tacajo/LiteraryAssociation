package com.LiteraryAssociation.service.impl;

import com.LiteraryAssociation.model.Notes;
import com.LiteraryAssociation.repository.NotesRepository;
import com.LiteraryAssociation.service.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotesServiceImpl implements NotesService {

    @Autowired
    private NotesRepository notesRepository;

    @Override
    public Notes save(Notes notes) {
        return notesRepository.save(notes);
    }

    @Override
    public List<Notes> findAll() {
        return notesRepository.findAll();
    }
}
