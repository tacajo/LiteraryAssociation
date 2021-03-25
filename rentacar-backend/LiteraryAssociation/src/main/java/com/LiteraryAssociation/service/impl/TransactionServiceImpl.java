package com.LiteraryAssociation.service.impl;

import com.LiteraryAssociation.model.Book;
import com.LiteraryAssociation.model.Transaction;
import com.LiteraryAssociation.model.User;
import com.LiteraryAssociation.repository.TransactionRepository;
import com.LiteraryAssociation.service.TransactionService;
import com.LiteraryAssociation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserService userService;

    public Transaction addTransaction(Double amount) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user = userService.findByUsername(user.getUsername());

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setUuid(UUID.randomUUID().toString());
        transaction.setUsername(user.getUsername());
        for (Book book:user.getCart()) {
            transaction.getBooks().add(book);
        }

        user.setCart(new ArrayList<>());
        userService.save(user);
        return transactionRepository.save(transaction);
    }

    @Override
    public ResponseEntity<?> editTransaction(boolean flag, Long id) {

        Optional<Transaction> transaction = transactionRepository.findById(id);
        if(flag){
            transaction.get().setSuccessful(true);
        }else{
            transaction.get().setSuccessful(false);
        }
        transactionRepository.save(transaction.get());
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @Override
    public Transaction findByUuid(String uuid) {
        return transactionRepository.findByUuid(uuid);
    }
}
