package com.paypal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDTO {
    private String uuid;
    private Long amount;
    private Long luport;
}
