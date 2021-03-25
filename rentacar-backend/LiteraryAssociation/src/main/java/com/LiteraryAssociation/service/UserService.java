package com.LiteraryAssociation.service;

import com.LiteraryAssociation.model.Book;
import com.LiteraryAssociation.model.User;

public interface UserService {

    User findByUsername(String username);

    User save(User user);

    void addBookToCart(Book book);
}
