package com.LiteraryAssociation.repository;

import com.LiteraryAssociation.model.CommitteeMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommitteeMemberRepository extends JpaRepository<CommitteeMember, Long> {
}
