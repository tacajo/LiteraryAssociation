package com.LiteraryAssociation.handler;

import com.LiteraryAssociation.model.User;
import com.LiteraryAssociation.model.VerificationToken;
import com.LiteraryAssociation.model.Writer;
import com.LiteraryAssociation.repository.UserRepository;
import com.LiteraryAssociation.repository.VerificationTokenRepository;
import com.LiteraryAssociation.repository.WriterRepository;
import com.LiteraryAssociation.service.UserService;
import com.LiteraryAssociation.service.WriterService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class SendActivationMail implements JavaDelegate {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public JavaMailSender emailSender;

    @Autowired
    private UserService userService;

    @Autowired
    private Environment env;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Override
    @Async
    public void execute(DelegateExecution delegateExecution) throws Exception {

        logger.info("usao u slanje mejla");
        String email = "";
        String username = "";

        HashMap<String, Object> registrationData = (HashMap<String, Object>) delegateExecution.getVariable("formValue");
        Object o = registrationData.get("email");
        email = o.toString();

        Object o1 = registrationData.get("username");
        username = o1.toString();

        System.out.println(username);

        User user = userService.findByUsername(username);

        VerificationToken confirmationToken = new VerificationToken(user);
        verificationTokenRepository.save(confirmationToken);

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(email);
        mail.setSubject("Complete Registration!");
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setText("To confirm your account, please click here : "
                +"http://localhost:4200/confirm-account?ct="+confirmationToken.getConfirmationToken());
        try{
            emailSender.send(mail);
            logger.info("poslao mejl");
        }
        catch( Exception e ){
            e.printStackTrace();
            System.out.println("javaMailSender.send(mail) nije prosao" );
        }


    }
}
