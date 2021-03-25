package com.LiteraryAssociation.repository;

import com.LiteraryAssociation.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    List<Genre> findAll();

    Genre findByName(String name);
}
