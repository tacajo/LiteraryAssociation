package com.LiteraryAssociation.handler;

import com.LiteraryAssociation.repository.WriterRepository;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ValidateData implements JavaDelegate {

    @Autowired
    private WriterRepository writerRepository;

    @Autowired
    private RuntimeService runtimeService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        HashMap<String, Object> registrationData = (HashMap<String, Object>) delegateExecution.getVariable("formValue");
        System.out.println("registration data " + registrationData);


        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(registrationData.get("email").toString());

        System.out.println(matcher.matches());

        if(matcher.matches()
                && registrationData.get("password").toString().equals(registrationData.get("rePassword").toString())
                && writerRepository.findByUsername(registrationData.get("username").toString()) == null){
            delegateExecution.setVariable("validData", true);
            System.out.println("tacni podaci");
        } else {
            System.out.println("netacni podaci");
            delegateExecution.setVariable("validData", false);
        }
    }
}
