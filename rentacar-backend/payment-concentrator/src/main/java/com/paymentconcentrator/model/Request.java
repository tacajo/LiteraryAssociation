package com.paymentconcentrator.model;

import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String merchantid;

    @Column
    private String merchant_password;

    @Column
    private Long amount;

    @Column
    private String merchantOrderId;

    @Column
    private DateTime merchantTimestamp;

    @Column
    private String success_url;

    @Column
    private String failed_url;

    @Column
    private String error_url;
}
