package com.LiteraryAssociation.service;

import com.LiteraryAssociation.model.Genre;
import com.LiteraryAssociation.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface GenreService {

    Genre findByName(String name);

    List<Genre> findAll();
}
