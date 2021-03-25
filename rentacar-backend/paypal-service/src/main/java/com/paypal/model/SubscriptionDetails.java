package com.paypal.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class SubscriptionDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long subscriberDataID;

    @Column
    private String status;

    @Column
    private String uuidLU;

}
