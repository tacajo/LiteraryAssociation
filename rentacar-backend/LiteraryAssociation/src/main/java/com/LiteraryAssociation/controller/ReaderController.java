package com.LiteraryAssociation.controller;

import com.LiteraryAssociation.dto.FormFieldDTO;
import com.LiteraryAssociation.dto.FormValuesDto;
import com.LiteraryAssociation.model.Genre;
import com.LiteraryAssociation.service.GenreService;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorException;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/reader", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReaderController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private FormService formService;

    @Autowired
    private GenreService genreService;

    @GetMapping
    public ResponseEntity startProcess() {
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("reader_registration");

        return new ResponseEntity(pi.getId(), HttpStatus.OK);
    }

    @GetMapping(value = "/{processId}")
    public FormFieldDTO getFields(@PathVariable("processId")String processId) {
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

    @PostMapping(path = "entry-data/{taskId}", produces = "application/json")
    public ResponseEntity<?> dataEntry(@RequestBody List<FormValuesDto> dto, @PathVariable String taskId) {
        System.out.println(taskId);
        HashMap<String, Object> map = this.mapListToDto(dto);

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        runtimeService.setVariable(processInstanceId, "registration", map);
        logger.info("registration: " + map);
        try{
            taskService.complete(taskId);
        }catch (FormFieldValidatorException e) {
            return new ResponseEntity<>("Pogresno uneti podaci!", HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<>(HttpStatus.OK);
    }

    private HashMap<String, Object> mapListToDto(List<FormValuesDto> list) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (FormValuesDto temp : list) {
            map.put(temp.getFieldId(), temp.getFieldValue());
        }

        return map;
    }
}
