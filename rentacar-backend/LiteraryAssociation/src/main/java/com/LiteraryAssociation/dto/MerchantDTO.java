package com.LiteraryAssociation.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MerchantDTO {

    private Long id;

    private boolean registered;

    private List<String> paymentWays =  new ArrayList<>();

    private String uuid;
}
