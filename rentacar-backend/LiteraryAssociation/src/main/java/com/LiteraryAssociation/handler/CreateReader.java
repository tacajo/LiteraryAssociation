package com.LiteraryAssociation.handler;

import com.LiteraryAssociation.model.Genre;
import com.LiteraryAssociation.model.Reader;
import com.LiteraryAssociation.model.Role;
import com.LiteraryAssociation.repository.RoleRepository;
import com.LiteraryAssociation.service.GenreService;
import com.LiteraryAssociation.service.ReaderService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class CreateReader implements JavaDelegate {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ReaderService readerService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private GenreService genreService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        logger.info("reader registration.. creating reader...");
        HashMap<String, Object> registrationData = (HashMap<String, Object>) delegateExecution.getVariable("reader");
        System.out.println("registration data " + registrationData);

        Role role = roleRepository.findById(2L).get();

        Reader reader = new Reader();
        reader.getRoles().add(role);
        reader.setFirstName(registrationData.get("firstName").toString());
        reader.setLastName(registrationData.get("lastName").toString());
        reader.setCity(registrationData.get("city").toString());
        reader.setPassword(passwordEncoder.encode(registrationData.get("password").toString()));
        reader.setUsername(registrationData.get("username").toString());
        reader.setCountry(registrationData.get("country").toString());
        reader.setEmail(registrationData.get("email").toString());
        reader = readerService.save(reader);

        Genre g = genreService.findByName(registrationData.get("genre").toString());
        g.getUsers().add(reader);
        reader.getGenres().add(g);

        if (registrationData.get("betaReader").equals("true")) {
            HashMap<String, Object> genre = (HashMap<String, Object>) delegateExecution.getVariable("formValue");
            g = genreService.findByName(registrationData.get("genre").toString());
            g.getUsers().add(reader);
            reader.getInterestedGenres().add(g);
            reader.setBetaReader(true);
        } else {
            reader.setBetaReader(false);
        }
        readerService.save(reader);

        logger.info("reader is added to the database");
        delegateExecution.setVariable("formValue", registrationData);

    }
}
