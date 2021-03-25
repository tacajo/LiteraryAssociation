package com.bitcoinservice.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BitcoinDTO {

    private String order_id;

    private double price_amount;

    private String price_currency;

    private String receive_currency;

    private String title;

    private String description;

    private String callback_url;

    private String cancel_url;

    private String success_url;

    private String token;


}
