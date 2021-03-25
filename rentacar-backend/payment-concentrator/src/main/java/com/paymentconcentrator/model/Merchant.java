package com.paymentconcentrator.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String merchantid;

    @Column
    private String merchant_password;

    @Column
    private String luname;

    @Column
    private Long luport;

    @OneToMany
    private List<PaymentWay> paymentWays = new ArrayList<>();

    @Column
    private String lu;
}
