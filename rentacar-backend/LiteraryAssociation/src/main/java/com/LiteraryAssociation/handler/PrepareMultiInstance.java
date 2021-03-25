package com.LiteraryAssociation.handler;

import com.LiteraryAssociation.model.CommitteeMember;
import com.LiteraryAssociation.service.CommitteeMemberService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PrepareMultiInstance implements JavaDelegate {

    @Autowired
    private CommitteeMemberService committeeMemberService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("usao u prepare Multi Instance");
        List<String> committeeMembers = new ArrayList<>();
        List<CommitteeMember> committeeMembersDB = committeeMemberService.findAll();
        for (CommitteeMember committeeMember : committeeMembersDB) {
            committeeMembers.add(committeeMember.getUsername());
        }
        delegateExecution.setVariable("committeeMembers", committeeMembers);
    }
}
