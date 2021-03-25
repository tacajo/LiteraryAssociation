package com.LiteraryAssociation.service.impl;

import com.LiteraryAssociation.model.Book;
import com.LiteraryAssociation.repository.BookRepository;
import com.LiteraryAssociation.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book findOneById(Long id) {
        return bookRepository.findOneById(id);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }
}
