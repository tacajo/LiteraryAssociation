package com.LiteraryAssociation.service;

import com.LiteraryAssociation.model.Notes;

import java.util.List;

public interface NotesService {

    Notes save(Notes notes);

    List<Notes> findAll();
}
