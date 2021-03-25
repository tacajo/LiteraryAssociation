package com.bankservice.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String pan;

    @Column
    private Long balance;

    @Column
    private String securityCode;

    @Column
    private String cardHolderName;

    @Column
    private String date;

    @ManyToOne
    private Bank bank;


}
