package com.LiteraryAssociation.controller;

import com.LiteraryAssociation.dto.BookDTO;
import com.LiteraryAssociation.dto.TransactionDTO;
import com.LiteraryAssociation.dto.WriterDTO;
import com.LiteraryAssociation.model.Book;
import com.LiteraryAssociation.model.Transaction;
import com.LiteraryAssociation.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "https://localhost:4200")
@RequestMapping(value = "/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public TransactionDTO addTransaction(@RequestBody Double amount) {
        TransactionDTO dto = new TransactionDTO();
        Transaction transaction = transactionService.addTransaction(amount);
        dto.setUuid(transaction.getUuid());
        dto.setUsername(transaction.getUsername());
        return dto;
    }

    @PutMapping(value = "/{flag}/{id}")
    public ResponseEntity<?> editTransaction(@PathVariable("flag") boolean flag, @PathVariable("id") Long id){
        return transactionService.editTransaction(flag, id);
    }

    @GetMapping(value = "/{uuid}")
    public TransactionDTO getTransaction(@PathVariable String uuid) {
        Transaction  transaction= transactionService.findByUuid(uuid);
        TransactionDTO dto = new TransactionDTO();
        Double amount = 0D;
        for (Book book:transaction.getBooks()) {
            amount += book.getPrice();
            BookDTO bookDTO = new BookDTO();
            WriterDTO writerDTO = new WriterDTO();
            writerDTO.setLastName(book.getWriter().getLastName());
            writerDTO.setFirstName(book.getWriter().getFirstName());
            bookDTO.setWriter(writerDTO);
            bookDTO.setTitle(book.getTitle());
            bookDTO.setPrice(book.getPrice());
            dto.getBooks().add(bookDTO);
        }
        dto.setAmount(amount);
        dto.setUuid(uuid);

        return dto;
    }
}
