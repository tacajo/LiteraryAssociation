package com.bitcoinservice.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    public String currency;

    @Column(nullable = true)
    public String PaypalPaymentID;

    @Column(nullable = true)
    public String total;

    @Column(nullable = true)
    public String details;

    @Column(nullable = true)
    public String state;

    @Column
    private Long amount;

    @Column
    private boolean processed;

    @Column
    private String uuid;

    @Column(nullable = true)
    public String payer;

    @Column(nullable = true)
    public String transactions;

    @Column
    public String luUuid;

    @Column
    public String username;
}
