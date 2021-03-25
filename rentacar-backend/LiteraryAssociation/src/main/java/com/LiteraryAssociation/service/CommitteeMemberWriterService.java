package com.LiteraryAssociation.service;

import com.LiteraryAssociation.model.CommitteMemberWriter;

public interface CommitteeMemberWriterService {

    boolean alreadyCommented(Long writerId, Integer numOfSending);

    boolean addOpinion(Long writerId, CommitteMemberWriter committeMemberWriter, Integer numOfSending);

    int resultOfOpinion(Long writerId, Integer numOfSending);
}
