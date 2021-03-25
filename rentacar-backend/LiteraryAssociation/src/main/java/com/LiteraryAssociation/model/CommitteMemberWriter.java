package com.LiteraryAssociation.model;

import com.LiteraryAssociation.enums.Opinion;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class CommitteMemberWriter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "committeeMember_id", referencedColumnName = "id")
    private CommitteeMember committeeMember;

    @JsonIgnore
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "writer_id", referencedColumnName = "id")
    private Writer writer;

    @Column
    private Opinion opinion;

    @Column
    private String comment;

    @Column
    private Integer numOfSending = 0;
}
