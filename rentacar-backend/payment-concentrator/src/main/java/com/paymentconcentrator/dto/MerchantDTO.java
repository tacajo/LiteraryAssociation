package com.paymentconcentrator.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
public class MerchantDTO {

    private Long id;

    private boolean registered;

    private List<String> paymentWays =  new ArrayList<>();

    private Long luport;

    private String luname;

    private String uuid;
}
