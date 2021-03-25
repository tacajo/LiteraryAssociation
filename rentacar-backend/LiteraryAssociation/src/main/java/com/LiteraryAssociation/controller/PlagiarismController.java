package com.LiteraryAssociation.controller;

import com.LiteraryAssociation.dto.FormFieldDTO;
import com.LiteraryAssociation.dto.FormValuesDto;
import com.LiteraryAssociation.model.Book;
import com.LiteraryAssociation.model.Editor;
import com.LiteraryAssociation.service.BookService;
import com.LiteraryAssociation.service.EditorService;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/plagiarism", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlagiarismController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    IdentityService identityService;

    @Autowired
    private EditorService editorService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private BookService bookService;

    @Autowired
    TaskService taskService;

    @Autowired
    FormService formService;

    @GetMapping
    public FormFieldDTO runPlagiarismProcess() {
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("utvrdjivanje_plagijarizma");
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);

        runtimeService.setVariable(pi.getId(), "processId", pi.getId());
        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();

        return new FormFieldDTO(task.getId(), pi.getId(), properties);
    }

    @GetMapping(value="/editors")
    public List<Editor> getAllEditors() {
        return this.editorService.findAll();
    }


    @PostMapping(path = "entry-data/{taskId}", produces = "application/json")
    public ResponseEntity<?> dataEntry(@RequestBody List<FormValuesDto> dto, @PathVariable String taskId) {

        HashMap<String, Object> map = this.mapListToDto(dto);

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        runtimeService.setVariable(processInstanceId, "formValue", map);
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

}
