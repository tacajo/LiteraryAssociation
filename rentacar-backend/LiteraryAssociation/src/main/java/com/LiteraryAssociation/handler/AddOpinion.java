package com.LiteraryAssociation.handler;

import com.LiteraryAssociation.enums.Opinion;
import com.LiteraryAssociation.model.CommitteMemberWriter;
import com.LiteraryAssociation.model.CommitteeMember;
import com.LiteraryAssociation.model.Writer;
import com.LiteraryAssociation.service.CommitteeMemberWriterService;
import com.LiteraryAssociation.service.WriterService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AddOpinion implements JavaDelegate {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CommitteeMemberWriterService committeeMemberWriterService;

    @Autowired
    private WriterService writerService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        HashMap<String, Object> opinion = (HashMap<String, Object>) delegateExecution.getVariable("formValue");
        System.out.println(opinion);

        Writer writer = writerService.findById((Long)delegateExecution.getVariable("writerId"));
        CommitteeMember committeeMember = (CommitteeMember) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Integer numOfSending = (Integer)delegateExecution.getVariable("numOfSending");

        CommitteMemberWriter committeMemberWriter = new CommitteMemberWriter();
        if(opinion.get("komentar") != null)
            committeMemberWriter.setComment(opinion.get("komentar").toString());
        if(opinion.get("misljenje") != null)
            committeMemberWriter.setOpinion(Opinion.valueOf(opinion.get("misljenje").toString()));
        committeMemberWriter.setNumOfSending((Integer) numOfSending);
        boolean endTask = committeeMemberWriterService.addOpinion(writer.getId(),
                committeMemberWriter, numOfSending);
        System.out.println("da li su svi glasali? " + endTask);
        if(endTask) {
            int result = committeeMemberWriterService.resultOfOpinion(writer.getId(), numOfSending);
            delegateExecution.setVariable("sviGlasali", true);
            delegateExecution.setVariable("rezultat", result);
            System.out.println("rezultat glasanja " + result);
            String message = "";
            if(result == 1) {
                message = "You are not suitable to be a writer.";
            } else if(result == 2) {
                message = "You are suitable to be a writer.\n\nCongratulation! You are new member of association";
            } else {
                message = "You have to add more pdfs.";
            }
            logger.info("result of giving opinion: " + result);
            delegateExecution.setVariable( "message", message);
        } else {
            delegateExecution.setVariable("sviGlasali", false);
        }

    }
}
