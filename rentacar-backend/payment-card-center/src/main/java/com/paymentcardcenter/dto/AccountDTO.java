package com.paymentcardcenter.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountDTO {

    private Integer pan;

    private String securityCode;

    private String cardHolderName;

    private String date;

}
