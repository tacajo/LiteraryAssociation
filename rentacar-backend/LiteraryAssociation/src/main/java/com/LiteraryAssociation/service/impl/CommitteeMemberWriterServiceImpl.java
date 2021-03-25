package com.LiteraryAssociation.service.impl;

import com.LiteraryAssociation.enums.Opinion;
import com.LiteraryAssociation.model.CommitteMemberWriter;
import com.LiteraryAssociation.model.CommitteeMember;
import com.LiteraryAssociation.model.User;
import com.LiteraryAssociation.model.Writer;
import com.LiteraryAssociation.repository.CommitteMemberWriterRepository;
import com.LiteraryAssociation.service.CommitteeMemberService;
import com.LiteraryAssociation.service.CommitteeMemberWriterService;
import com.LiteraryAssociation.service.WriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommitteeMemberWriterServiceImpl implements CommitteeMemberWriterService {

    @Autowired
    private CommitteMemberWriterRepository repository;

    @Autowired
    private WriterService writerService;

    @Autowired
    private CommitteeMemberService committeeMemberService;

    @Override
    public boolean alreadyCommented(Long writerId, Integer numOfSending) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<CommitteMemberWriter> committeeMembersWriters = repository.findAll();
        for (CommitteMemberWriter cmw : committeeMembersWriters) {
            if (cmw.getCommitteeMember().getId() == user.getId() && cmw.getWriter().getId() == writerId &&
                    cmw.getNumOfSending() == numOfSending) {
                System.out.println("ovaj clan je vec glasao");
                return true;
            }

        }
        return false;
    }

    @Override
    public boolean addOpinion(Long writerId, CommitteMemberWriter committeMemberWriter, Integer numOfSending) {
        CommitteeMember user = (CommitteeMember) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        committeMemberWriter.setWriter(writerService.findById(writerId));
        committeMemberWriter.setCommitteeMember(user);
        repository.save(committeMemberWriter);

        List<CommitteeMember> cm1 = committeeMemberService.findAll();
        List<CommitteMemberWriter> cm2 = repository.findByWriterAndNumOfSending(committeMemberWriter.getWriter(),
                numOfSending);

        System.out.println("stigao dovde");
        if (cm1.size() == cm2.size()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int resultOfOpinion(Long writerId, Integer numOfSending) {
        List<CommitteMemberWriter> cm1 = repository.findByWriterAndNumOfSending(writerService.findById(writerId),
                numOfSending);
        List<CommitteMemberWriter> cm2 = repository.findByOpinionAndWriterAndNumOfSending(Opinion.PODOBAN,
                writerService.findById(writerId), numOfSending);
        List<CommitteMemberWriter> cm3 = repository.findByOpinionAndWriterAndNumOfSending(Opinion.NIJE_PODOBAN,
                writerService.findById(writerId), numOfSending);
        List<CommitteMemberWriter> cm4 = repository.findByOpinionAndWriterAndNumOfSending(Opinion.JOS_MATERIJALA,
                writerService.findById(writerId), numOfSending);
        Writer writer = writerService.findById(writerId);

        System.out.println("broj podobnih " + cm2.size());
        System.out.println("broj nije podobnih" + cm3.size());
        System.out.println("broj jos materijala" + cm4.size());
        writer.setWaitingForVotes(false);

        if (cm4.size() > 0) {
            writerService.save(writer);
            return 3;
        } else if (cm1.size() == cm2.size()) {
            writer.setMember(true);
            writerService.save(writer);
            return 2;
        } else if (cm3.size() > 0) {
            writerService.save(writer);
            return 1;
        } else {
            return 1;
        }

    }
}
