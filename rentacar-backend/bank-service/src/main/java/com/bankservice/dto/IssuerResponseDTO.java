package com.bankservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class IssuerResponseDTO {

    private String acquirerOrderId;

    private String acquirerTimestamp;

    private String issuerOrderId;

    private String issuerTimestamp;

    private String auth;

    private String state;

    private boolean processed;

}
