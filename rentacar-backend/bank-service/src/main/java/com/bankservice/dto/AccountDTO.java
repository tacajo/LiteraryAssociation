package com.bankservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountDTO {

    private String pan;

    private String securityCode;

    private String cardHolderName;

    private String date;
}
