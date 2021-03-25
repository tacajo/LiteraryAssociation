package com.LiteraryAssociation.service;

import com.LiteraryAssociation.model.Reader;

import java.util.List;

public interface ReaderService {

    Reader save(Reader reader);

    Reader findByUsername(String username);

    Reader findById(Long id);

    List<Reader> findAll();

    List<Reader> findByBetaReaderTrue();

}
