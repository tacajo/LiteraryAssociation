package com.LiteraryAssociation.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private boolean registered;

    @OneToMany
    private List<PaymentWay> paymentWays;

    @Column
    private String uuid;
}
