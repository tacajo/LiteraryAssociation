package com.LiteraryAssociation.handler;

import com.LiteraryAssociation.model.Reader;
import com.LiteraryAssociation.service.ReaderService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BetaReaderNegativePoint  implements JavaDelegate {

    @Autowired
    private ReaderService readerService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("usao u BetaReaderNegativePoint");
        String id = delegateExecution.getVariable("betaReader").toString();
        Reader reader = readerService.findById(Long.parseLong(id));
        reader.setPoints(reader.getPoints() - 1);
        delegateExecution.setVariable("kazneniBodovi", reader.getPoints());

        readerService.save(reader);
    }
}
