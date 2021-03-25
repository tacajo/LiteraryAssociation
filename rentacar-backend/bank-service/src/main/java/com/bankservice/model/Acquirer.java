package com.bankservice.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Acquirer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String merchant_id;

    @Column
    private String merchant_password;

    @Column
    private String lu;

    @OneToOne
    private Account account;
}
