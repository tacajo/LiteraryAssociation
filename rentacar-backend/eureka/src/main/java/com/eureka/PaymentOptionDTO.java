package com.eureka;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PaymentOptionDTO {

    private List<String> option = new ArrayList<>();
}
