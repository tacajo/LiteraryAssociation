package com.LiteraryAssociation.handler;

import com.LiteraryAssociation.service.WriterService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WriterBecomesMember implements JavaDelegate {

    @Autowired
    private WriterService writerService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        writerService.member((Long) delegateExecution.getVariable("writerId"));
        logger.info("writer with id " + delegateExecution.getVariable("writerId") + "has became a member.");

    }
}
