package com.LiteraryAssociation.controller;

import com.LiteraryAssociation.dto.BookDTO;
import com.LiteraryAssociation.dto.WriterDTO;
import com.LiteraryAssociation.model.Book;
import com.LiteraryAssociation.model.User;
import com.LiteraryAssociation.service.BookService;
import com.LiteraryAssociation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/book", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<BookDTO> getBooks() {
        List<BookDTO> books = new ArrayList<>();

        for (Book book: bookService.findAll()) {
            BookDTO bookDTO = new BookDTO();
            WriterDTO writerDTO = new WriterDTO();
            writerDTO.setLastName(book.getWriter().getLastName());
            writerDTO.setFirstName(book.getWriter().getFirstName());
            bookDTO.setId(book.getId());
            bookDTO.setWriter(writerDTO);
            bookDTO.setCity(book.getCity());
            bookDTO.setIsbn(book.getIsbn());
            bookDTO.setPageNumber(book.getPageNumber());
            bookDTO.setTitle(book.getTitle());
            bookDTO.setYear(book.getYear());
            bookDTO.setPrice(book.getPrice());
            books.add(bookDTO);
        }

        return books;
    }

    @PutMapping(value = "/{bookId}")
    public void addToCart(@PathVariable Long bookId) {
        Book book = bookService.findOneById(bookId);
        userService.addBookToCart(book);
    }


    @GetMapping(value = "/user")
    public List<BookDTO> getUserBooks() {
        List<BookDTO> books = new ArrayList<>();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user = userService.findByUsername(user.getUsername());

        if(user.getCart().size() > 0) {
            for (Book book : user.getCart()) {
                BookDTO bookDTO = new BookDTO();
                WriterDTO writerDTO = new WriterDTO();
                writerDTO.setLastName(book.getWriter().getLastName());
                writerDTO.setFirstName(book.getWriter().getFirstName());
                bookDTO.setId(book.getId());
                bookDTO.setWriter(writerDTO);
                bookDTO.setCity(book.getCity());
                bookDTO.setIsbn(book.getIsbn());
                bookDTO.setPageNumber(book.getPageNumber());
                bookDTO.setTitle(book.getTitle());
                bookDTO.setYear(book.getYear());
                bookDTO.setPrice(book.getPrice());
                books.add(bookDTO);
            }
        }

        return books;
    }
}
