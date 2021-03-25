package com.LiteraryAssociation.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Double amount;

    @Column
    private String uuid;

    @ManyToMany
    private List<Book> books = new ArrayList<>();

    @Column
    private boolean successful;

    @Column
    private String username;
}
