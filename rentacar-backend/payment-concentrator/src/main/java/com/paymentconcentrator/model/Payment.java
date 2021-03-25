package com.paymentconcentrator.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long amount;

    @Column
    private String uuid;

    @Column
    private Long luport;

    @Column
    private boolean processed;

    @Column(nullable = false)
    public String currency;

    @Column(nullable = false)
    public String total;

    @Column
    public String details = "";

    @Column(nullable = false)
    public String state;

    @Column
    public String luUuid;

    @Column
    public String username;

    @Column
    public LocalDate date;

}
