package com.LiteraryAssociation.service.impl;

import com.LiteraryAssociation.model.Genre;
import com.LiteraryAssociation.repository.GenreRepository;
import com.LiteraryAssociation.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    @Autowired
    private GenreRepository genreRepository;

    @Override
    public Genre findByName(String name) {
        return genreRepository.findByName(name);
    }

    @Override
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }
}
