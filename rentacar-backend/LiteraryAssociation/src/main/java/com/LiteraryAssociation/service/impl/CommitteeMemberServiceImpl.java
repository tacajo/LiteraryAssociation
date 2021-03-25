package com.LiteraryAssociation.service.impl;

import com.LiteraryAssociation.model.CommitteeMember;
import com.LiteraryAssociation.repository.CommitteeMemberRepository;
import com.LiteraryAssociation.service.CommitteeMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommitteeMemberServiceImpl implements CommitteeMemberService {

    @Autowired
    private CommitteeMemberRepository committeeMemberRepository;

    @Override
    public List<CommitteeMember> findAll() {
        return committeeMemberRepository.findAll();
    }


}
