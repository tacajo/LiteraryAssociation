package com.bitcoinservice.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BitcoinResponseDTO {

    private Long id;

    private String status;

    private boolean do_not_convert;

    private String price_currency;

    private String price_amount;

    private boolean lightning_network;

    private String receive_currency;

    private String receive_amount;

    private String created_at;

    private String order_id;

    private String payment_url;

    private String underpaid_amount;

    private String overpaid_amount;

    private boolean is_refundable;

    private String token;
}
