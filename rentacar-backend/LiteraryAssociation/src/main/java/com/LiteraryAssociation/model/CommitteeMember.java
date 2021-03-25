package com.LiteraryAssociation.model;

import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@DiscriminatorValue("committeeMember")
public class CommitteeMember extends User{

    @JsonIgnore
    @OneToMany(mappedBy = "committeeMember")
    private List<CommitteMemberWriter> committeMemberWriters;

}
