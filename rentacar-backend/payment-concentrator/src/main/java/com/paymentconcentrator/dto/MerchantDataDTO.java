package com.paymentconcentrator.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MerchantDataDTO {

    private String merchant_id;

    private String merchant_password;
}
