package com.LiteraryAssociation.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@DiscriminatorValue("reader")
public class Reader extends User {

    @Column
    private  boolean betaReader;

    @Column(nullable = true)
    private Integer points = 0;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private VerificationToken verificationToken;

    @JsonBackReference
    @ManyToMany
    private List<Genre> interestedGenres = new ArrayList<>();
}
