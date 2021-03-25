package com.bankservice.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
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

    @Column
    public String luUuid;

    @Column
    public String username;

    @Column(nullable = false)
    public String state;

}
