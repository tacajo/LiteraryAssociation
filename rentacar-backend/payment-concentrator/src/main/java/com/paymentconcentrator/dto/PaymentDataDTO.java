package com.paymentconcentrator.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDataDTO {

    private Integer payment_id;

    private String payment_url;
}
