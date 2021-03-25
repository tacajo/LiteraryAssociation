package com.bankservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MerchantDataDTO {

    private String merchant_id;

    private String merchant_password;
}
