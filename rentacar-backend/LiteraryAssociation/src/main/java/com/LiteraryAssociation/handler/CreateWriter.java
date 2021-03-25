package com.LiteraryAssociation.handler;

import com.LiteraryAssociation.model.Genre;
import com.LiteraryAssociation.model.Role;
import com.LiteraryAssociation.model.Writer;
import com.LiteraryAssociation.repository.RoleRepository;
import com.LiteraryAssociation.service.GenreService;
import com.LiteraryAssociation.service.WriterService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;


@Service
public class CreateWriter implements JavaDelegate {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WriterService writerService;

    @Autowired
    TaskService taskService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private GenreService genreService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("usao u kreiranje korisnika");

        logger.info("usao u kreiranje korisnika");
        HashMap<String, Object> registrationData = (HashMap<String, Object>) delegateExecution.getVariable("formValue");
        System.out.println("registration data " + registrationData);
        Role role = roleRepository.findById(1L).get();

        Writer writer = new Writer();
        writer.getRoles().add(role);
        writer.setFirstName(registrationData.get("firstName").toString());
        writer.setLastName(registrationData.get("lastName").toString());
        writer.setCity(registrationData.get("city").toString());
        writer.setPassword(passwordEncoder.encode(registrationData.get("password").toString()));
        writer.setUsername(registrationData.get("username").toString());
        writer.setCountry(registrationData.get("country").toString());
        writer.setEmail(registrationData.get("email").toString());
        writer.setMembershipFeePaid(false);
        writer.setEnabled(false);
        Writer writerDB = writerService.save(writer);

        List<String> genres = (List<String>) registrationData.get("genre");
        System.out.println("ZANROVI: " + genres);
        for (String genre : genres) {
            if (!genre.equals("")) {
                logger.info("genre " + genre);
                Genre g = genreService.findByName(genre);
                g.getUsers().add(writerDB);
                writer.getGenres().add(g);
                writerService.save(writerDB);
            }
        }

        delegateExecution.setVariable("writer", writer.getFirstName() + " " + writer.getLastName());
        delegateExecution.setVariable("writerId", writer.getId());
        delegateExecution.setVariable("numOfSending", 0);
        delegateExecution.setVariable("writerUsername", writer.getUsername());
        logger.info("actuator: " + (String) delegateExecution.getVariable("actuator"));
        logger.info("upisan writer u bazu");


    }
}
