package com.LiteraryAssociation.service.impl;

import com.LiteraryAssociation.model.Book;
import com.LiteraryAssociation.model.User;
import com.LiteraryAssociation.repository.UserRepository;
import com.LiteraryAssociation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void addBookToCart(Book book) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user = findByUsername(user.getUsername());
        user.getCart().add(book);
        save(user);
    }


}
