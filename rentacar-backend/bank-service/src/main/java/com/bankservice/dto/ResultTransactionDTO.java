package com.bankservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResultTransactionDTO {

    private String url;

    private boolean success;
}
