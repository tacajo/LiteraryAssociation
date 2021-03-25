package com.LiteraryAssociation.repository;

import com.LiteraryAssociation.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findById(Long id);

    List<Book> findAll();

    Book findOneById(Long id);

    Book findByTitle(String title);
}
