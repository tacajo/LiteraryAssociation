package com.LiteraryAssociation.controller;

import com.LiteraryAssociation.dto.FormFieldDTO;
import com.LiteraryAssociation.dto.FormValuesDto;
import com.LiteraryAssociation.enums.Opinion;
import com.LiteraryAssociation.model.*;
import com.LiteraryAssociation.service.BookService;
import com.LiteraryAssociation.service.EditorService;
import com.LiteraryAssociation.service.GenreService;
import com.LiteraryAssociation.service.ReaderService;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorException;
import org.camunda.bpm.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin(origins = "https://localhost:4200")
@RequestMapping(value = "/form", produces = MediaType.APPLICATION_JSON_VALUE)
public class FormController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private BookService bookService;

    @Autowired
    private FormService formService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private EditorService editorService;

    @Autowired
    private ReaderService readerService;

    @GetMapping(value = "/{processId}", produces = MediaType.ALL_VALUE)
    public FormFieldDTO getForm(@PathVariable("processId") String processId) {
        Task task = taskService.createTaskQuery().processInstanceId(processId).list().get(0);
        System.out.println("assignee: " + task.getAssignee());
        TaskFormData tfd = formService.getTaskFormData(task.getId());
        if(!checkAssignee(task)) {

        }

        List<FormField> properties = tfd.getFormFields();
        System.out.println("----------------------------");
        System.out.println(task.getTaskDefinitionKey());
        System.out.println("----------------------------");
        for (FormField field : properties) {
            if (field.getId().equals("misljenje")) {
                EnumFormType enumType = (EnumFormType) field.getType();
                enumType.getValues().clear();
                enumType.getValues().put(Opinion.PODOBAN.toString(), Opinion.PODOBAN.toString());
                enumType.getValues().put(Opinion.NIJE_PODOBAN.toString(), Opinion.NIJE_PODOBAN.toString());
                enumType.getValues().put(Opinion.JOS_MATERIJALA.toString(), Opinion.JOS_MATERIJALA.toString());
            } else if (field.getId().equals("genre") || field.getId().equals("genre2")) {
                EnumFormType enumType = (EnumFormType) field.getType();
                enumType.getValues().clear();
                for (Genre genre : genreService.findAll()) {
                    enumType.getValues().put(genre.getName(), genre.getName());
                }
            } else if (field.getId().equals("editors")) {
                EnumFormType enumType = (EnumFormType) field.getType();
                enumType.getValues().clear();
                for (Editor editor : editorService.findAll()) {
                    enumType.getValues().put(editor.getId().toString(), editor.getId().toString());
                }
            } else if (field.getId().equals("betaReaders")) {
                EnumFormType enumType = (EnumFormType) field.getType();
                enumType.getValues().clear();
                Book book = bookService.findOneById((Long) runtimeService.getVariable(processId, "idKnjige"));
                for (Reader reader : readerService.findByBetaReaderTrue()) {
                    if (reader.getInterestedGenres().contains(book.getGenre())) {
                        enumType.getValues().put(reader.getId().toString(), reader.getId().toString());
                    }
                }
            }
        }

        return new FormFieldDTO(task.getId(), processId, properties);
    }

    @PostMapping(path = "/{taskId}", produces = "application/json")
    public ResponseEntity<?> submitForm(@RequestBody List<FormValuesDto> dto, @PathVariable String taskId) {

        HashMap<String, Object> map = this.mapListToDto(dto);

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        runtimeService.setVariable(processInstanceId, "formValue", map);
        System.out.println(map);

        try {
            formService.submitTaskForm(taskId, map);
        } catch (FormFieldValidatorException e) {
            return new ResponseEntity<>("Pogresno uneti podaci!", HttpStatus.BAD_REQUEST);
        }
        System.out.println("-------------------------------");
        System.out.println("submit forme uradjen " + task.getTaskDefinitionKey());
        System.out.println("-------------------------------");
        return new ResponseEntity<>(HttpStatus.OK);
    }


    private HashMap<String, Object> mapListToDto(List<FormValuesDto> list) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (FormValuesDto temp : list) {
            if (temp.getFieldValues() != null) {
                map.put(temp.getFieldId(), temp.getFieldValues());
            } else {
                map.put(temp.getFieldId(), temp.getFieldValue());
            }

        }

        return map;
    }

    private boolean checkAssignee(Task task) {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(task.getAssignee() == null || task.getAssignee().equals(user.getId().toString())) {
                System.out.println("dobar assignee");
                return true;
            } else{
                System.out.println("los assignee");
                return false;
            }
        } catch (Exception e) {
            return true;
        }

    }

}
