package com.LiteraryAssociation.service;

import com.LiteraryAssociation.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Book save(Book book);

    Book findOneById(Long id);

    List<Book> findAll();

    Book findByTitle(String title);
}
