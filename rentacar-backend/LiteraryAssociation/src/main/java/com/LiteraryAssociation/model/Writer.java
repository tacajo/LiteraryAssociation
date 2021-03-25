package com.LiteraryAssociation.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("writer")
public class Writer extends User {



    @JsonIgnore
    @OneToMany(mappedBy = "writer")
    private List<Pdf> pdfs;

    @OneToMany(mappedBy = "writer")
    private List<CommitteMemberWriter> committeMemberWriters;

    @Column(nullable = true)
    private Boolean waitingForVotes = false;

    @Column(nullable = true)
    private Boolean MembershipFeePaid = false;

    @Column(nullable = true)
    private Boolean member = false;

    @OneToMany
    private List<Book> books;

    public List<Pdf> getPdfs() {
        return pdfs;
    }

    public void setPdfs(List<Pdf> pdfs) {
        this.pdfs = pdfs;
    }
}
