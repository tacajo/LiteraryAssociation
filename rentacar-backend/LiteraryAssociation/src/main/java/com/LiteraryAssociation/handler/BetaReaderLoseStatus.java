package com.LiteraryAssociation.handler;

import com.LiteraryAssociation.model.Reader;
import com.LiteraryAssociation.service.ReaderService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BetaReaderLoseStatus implements JavaDelegate {

    @Autowired
    private ReaderService readerService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("usao u BetaReaderLoseStatus");
        Reader reader = readerService.findById((Long) delegateExecution.getVariable("betaReader"));
        reader.setBetaReader(false);
        readerService.save(reader);
    }
}
