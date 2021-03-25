package com.LiteraryAssociation.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @ManyToOne
    private Writer writer;

    @ManyToOne
    private Editor editor;

    @ManyToOne
    private Genre genre;

    @OneToMany
    private List<Pdf> pdfs;

    @Column
    private Long isbn;

    @Column
    private Long year;

    @Column
    private String city;

    @Column
    private Long pageNumber;

    @Column
    private String synopsis;

    @Column(nullable = true)
    private boolean accept;

    @Column
    private boolean original;

    @Column(nullable = true)
    private boolean print = false;

    @Column(nullable = true)
    private Double price;

}
