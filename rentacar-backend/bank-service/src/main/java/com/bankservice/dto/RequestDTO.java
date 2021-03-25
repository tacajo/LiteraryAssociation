package com.bankservice.dto;

import lombok.*;
import org.joda.time.DateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RequestDTO {

    private String merchantid;

    private String merchant_password;

    private Long amount;

    private String merchant_order_id;

    private DateTime merchant_timestamp;

    private String success_url;

    private String failed_url;

    private String error_url;
}
