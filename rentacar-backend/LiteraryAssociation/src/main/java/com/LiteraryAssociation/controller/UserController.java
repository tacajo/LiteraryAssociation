package com.LiteraryAssociation.controller;

import com.LiteraryAssociation.dto.FormFieldDTO;
import com.LiteraryAssociation.dto.FormValuesDto;
import com.LiteraryAssociation.model.*;
import com.LiteraryAssociation.repository.UserRepository;
import com.LiteraryAssociation.repository.VerificationTokenRepository;
import com.LiteraryAssociation.security.TokenUtils;
import com.LiteraryAssociation.security.auth.JwtAuthenticationRequest;
import com.LiteraryAssociation.service.GenreService;
import com.LiteraryAssociation.service.UserService;
import com.LiteraryAssociation.service.WriterService;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins = "https://localhost:4200")
@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    IdentityService identityService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    TaskService taskService;

    @Autowired
    FormService formService;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private WriterService writerService;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private GenreService genreService;

    @GetMapping
    public ResponseEntity runRegistration() {
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("upp_process_1");
        runtimeService.setVariable(pi.getId(), "processId", pi.getId());
        return new ResponseEntity(pi.getId(), HttpStatus.OK);
    }

    @GetMapping(value = "/{processId}")
    public FormFieldDTO getRegistration(@PathVariable("processId")String processId) {
        Task task = taskService.createTaskQuery().processInstanceId(processId).list().get(0);


        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        if(task.getTaskDefinitionKey().equals("unos_podataka")) {
            for (FormField field : properties) {
                if (field.getId().equals("genre")) {
                    EnumFormType enumType = (EnumFormType) field.getType();
                    enumType.getValues().clear();
                    for (Genre genre:genreService.findAll()) {
                        enumType.getValues().put(genre.getName(), genre.getName());
                    }
                }
            }
        }

        return new FormFieldDTO(task.getId(), processId, properties);
    }

    @GetMapping(value = "/getPdfs/{processInstanceId}")
    public FormFieldDTO getPdfs(@PathVariable("processInstanceId") String processInstanceId) {

        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        Task task = taskService.createTaskQuery().processInstanceId(  processInstance.getId()).list().get(0);

        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();

        return new FormFieldDTO(task.getId(), processInstanceId, properties);

    }

    @GetMapping(value = "/taskid")
    public ResponseEntity getTask() {

        ProcessInstance pi = runtimeService.startProcessInstanceByKey("upp_process_1");
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);
        System.out.println(task.getId());
        return new ResponseEntity<>(task.getId(), HttpStatus.OK);
    }


    @PostMapping(path = "entry-data/{taskId}", produces = "application/json")
    public ResponseEntity<?> dataEntry(@RequestBody List<FormValuesDto> dto, @PathVariable String taskId) {

        HashMap<String, Object> map = this.mapListToDto(dto);

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        runtimeService.setVariable(processInstanceId, "registration", map);

//        try{
//            formService.submitTaskForm(taskId, map);
//        }catch (FormFieldValidatorException e) {
//            return new ResponseEntity<>("Pogresno uneti podaci!", HttpStatus.BAD_REQUEST);
//        }
        taskService.complete(taskId);


        return new ResponseEntity<>(HttpStatus.OK);
    }

    private HashMap<String, Object> mapListToDto(List<FormValuesDto> list) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (FormValuesDto temp : list) {
            map.put(temp.getFieldId(), temp.getFieldValue());
        }

        return map;
    }

    @GetMapping(value = "/confirmAccount/{token}/{processId}")
    public User confirmAccount(@PathVariable("token") String confirmationToken, @PathVariable("processId") String processId) {
        System.out.println(confirmationToken);
        VerificationToken token = verificationTokenRepository.findByConfirmationToken(confirmationToken);
        System.out.println(token);
        User user = userService.findByUsername(token.getUser().getUsername());

        user.setEnabled(true);

        user = userService.save(user);
        logger.info("Successfully enabled account for user: " + user.getUsername());

        runtimeService.setVariable(processId, "potvrdjen", true);

        return user;
    }

    @PostMapping(value = "/upload-pdfs/{username}/{taskId}")
    public ResponseEntity<?> upload_pdfs(@RequestPart("pdfs") List<MultipartFile> files,
                                         @PathVariable("username") String username,
                                         @PathVariable("taskId") String taskId) throws IOException {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("ASSIGNEE: " + task.getAssignee());
        System.out.println("ASSIGNEE user: " + user.getId());

        if(task.getAssignee().equals(user.getId().toString())){
            System.out.println("DOBAR JE ASSIGNEE");
            return writerService.upload_pdfs(files, username, taskId);
        } else {
            return null;
        }

    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> loginUser(@RequestBody JwtAuthenticationRequest authenticationRequest) {
        System.out.println("usao + " + authenticationRequest.getUsername());
        User user = userService.findByUsername(authenticationRequest.getUsername());


        if (user != null && user.isEnabled()) {
//            List<Role> roles = user.getRoles();
//            for (Role role : roles) {
//                if (role.getAuthority().equals("ROLE_WRITER")) {
//                    Writer writer = writerService.findById(user.getId());
//                    if (!writer.isEvaluatedByCommitteeMembers()) {
//                        logger.error(String.format("User is not confirmed! Username: '%s'.", authenticationRequest.getUsername()));
//                        return new ResponseEntity<>(new UserTokenState("error", 0), HttpStatus.BAD_REQUEST);
//                    }
//                }
//            }
            if (BCrypt.checkpw(authenticationRequest.getPassword(), user.getPassword())) {
                logger.info(String.format("Successful login! Username: '%s'.", authenticationRequest.getUsername()));
            } else {
                logger.error(String.format("Incorrect password! Username: '%s'.", authenticationRequest.getUsername()));
                return new ResponseEntity<>(new UserTokenState("error", 0), HttpStatus.BAD_REQUEST);

            }

            if (!user.isEnabled()) {
                return new ResponseEntity<>(new UserTokenState("notActivated", 0), HttpStatus.BAD_REQUEST);

            }

            final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            user = (User) authentication.getPrincipal();
            String jwt = tokenUtils.generateToken(user.getUsername());
            int expiresIn = tokenUtils.getExpiredIn();
            return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
        } else {
            logger.error(String.format("No user found with username '%s'.", authenticationRequest.getUsername()));
            return new ResponseEntity<>(new UserTokenState("error", 0), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/user")
    public User getUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
