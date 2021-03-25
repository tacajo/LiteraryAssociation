package com.LiteraryAssociation.controller;

import com.LiteraryAssociation.dto.ExplanationDTO;
import com.LiteraryAssociation.dto.FormFieldDTO;
import com.LiteraryAssociation.enums.Opinion;
import com.LiteraryAssociation.model.Book;
import com.LiteraryAssociation.service.BookService;
import com.LiteraryAssociation.service.EditorService;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "https://localhost:4200")
@RestController
@RequestMapping(value = "/editor", produces = MediaType.APPLICATION_JSON_VALUE)
public class EditorController {

    @Autowired
    TaskService taskService;

    @Autowired
    FormService formService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private BookService bookService;

    @Autowired
    private EditorService editorService;

    @GetMapping(value = "/get-books/{task}")
    public List<FormFieldDTO> getBooks(@PathVariable("task") String taskUrl){

        List<ProcessInstance> processInstances =
                runtimeService.createProcessInstanceQuery()
                        .processDefinitionKey("izdavanje_knjige")
                        .active()
                        .list();

        List<FormFieldDTO> ret = new ArrayList<>();

        for (ProcessInstance processInstance : processInstances) {
            Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0);

            if (task.getTaskDefinitionKey().equals(taskUrl)) {

                TaskFormData tfd = formService.getTaskFormData(task.getId());
                List<FormField> properties = tfd.getFormFields();

                ret.add(new FormFieldDTO(task.getId(), processInstance.getId(), properties));
            }
        }

        if (ret.size() > 0)
            return ret;
        else
            return null;

    }

    @GetMapping(value = "/get-books-plagiarism-download")
    public List<FormFieldDTO> getBooksPlagiarismDownload(){

        List<ProcessInstance> processInstances =
                runtimeService.createProcessInstanceQuery()
                        .processDefinitionKey("utvrdjivanje_plagijarizma")
                        .active()
                        .list();

        List<FormFieldDTO> ret = new ArrayList<>();

        for (ProcessInstance processInstance : processInstances) {
            Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0);

            if (task.getTaskDefinitionKey().equals("urednik_skida_knjige")) {

                TaskFormData tfd = formService.getTaskFormData(task.getId());
                List<FormField> properties = tfd.getFormFields();

                ret.add(new FormFieldDTO(task.getId(), processInstance.getId(), properties));
            }
        }

        if (ret.size() > 0)
            return ret;
        else
            return null;

    }

    @GetMapping(value = "/accept/{processId}/{purpose}")
    public void accept(@PathVariable("processId") String processId, @PathVariable("purpose") String purpose){

        runtimeService.setVariable(processId, "accept", true);
        runtimeService.setVariable(processId, "original", true);
        Long idBook = (Long) runtimeService.getVariable(processId, "idKnjige");
        Book book = bookService.findOneById(idBook);
        if(purpose.equals("tasks")){
            book.setAccept(true);
        }else{ //chooseOriginal
            book.setOriginal(true);
        }
        bookService.save(book);

        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0);
        taskService.complete(task.getId());

    }

    @PostMapping(value = "/decline/{processId}")
    public void decline(@PathVariable("processId") String processId, @RequestBody ExplanationDTO explanationDTO){

        System.out.println(explanationDTO.getExplanation());

        runtimeService.setVariable(processId, "accept", false);
        runtimeService.setVariable(processId, "explanation", explanationDTO.getExplanation());
        runtimeService.setVariable(processId, "original", false);

        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0);
        taskService.complete(task.getId());


    }



}
