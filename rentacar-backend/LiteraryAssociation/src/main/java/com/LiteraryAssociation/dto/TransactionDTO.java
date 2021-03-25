package com.LiteraryAssociation.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TransactionDTO {

    private String uuid;

    private String username;

    private List<BookDTO> books = new ArrayList<>();

    private Double amount;
}
