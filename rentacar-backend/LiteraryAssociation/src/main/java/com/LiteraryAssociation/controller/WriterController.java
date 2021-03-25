package com.LiteraryAssociation.controller;


import com.LiteraryAssociation.dto.FormFieldDTO;
import com.LiteraryAssociation.dto.FormValuesDto;
import com.LiteraryAssociation.model.Genre;
import com.LiteraryAssociation.model.Writer;
import com.LiteraryAssociation.service.WriterService;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/writer", produces = MediaType.APPLICATION_JSON_VALUE)
public class WriterController {

    @Autowired
    private WriterService writerService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private FormService formService;

    @PutMapping(value = "/pay/{processId}")
    public void pay( @PathVariable String processId) {
        System.out.println("usao u pay");
        Task task = taskService.createTaskQuery().processInstanceId(processId).list().get(0);
        if(task.getTaskDefinitionKey().equals("pisac_pokrece_placanje")) {
            System.out.println("nasao task pisac_pokrece_placanje");
            taskService.complete(task.getId());
        }

    }

    @PutMapping
    public ResponseEntity payFee() {
        System.out.println("usao u payFee");

        if (writerService.isPaid()) {
            return new ResponseEntity("paid", HttpStatus.OK);
        }

        List<ProcessInstance> processInstances =
                runtimeService.createProcessInstanceQuery()
                        .processDefinitionKey("upp_process_1")
                        .active() // we only want the unsuspended process instances
                        .list();

        for (ProcessInstance instance : processInstances) {
            Task task = taskService.createTaskQuery().processInstanceId(instance.getId()).list().get(0);
            if (task.getTaskDefinitionKey().equals("placanje_clanarine")) {
                if (writerService.checkWriter((Long) runtimeService.getVariable(instance.getId(), "writerId"))) {
                    taskService.setAssignee(task.getId(),
                            runtimeService.getVariable(instance.getId(), "writerId").toString());
                    writerService.payPee();
                    taskService.complete(task.getId());
                    return new ResponseEntity("successful", HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity("no task", HttpStatus.OK);
    }

    @PostMapping(value = "/upload-pdfs")
    public ResponseEntity<?> upload_pdfs(@RequestPart("pdfs") List<MultipartFile> files) throws IOException {
        Writer writer = (Writer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return writerService.upload_pdfs(files, writer.getUsername(), writerService.findTaskUploadPds());
    }

    @GetMapping(value = "/{username}")
    public FormFieldDTO runPublishingABook(@PathVariable("username") String username) {
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("izdavanje_knjige");
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);

        runtimeService.setVariable(pi.getId(), "publishingProcessId", pi.getId());

        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();

        Writer writer = writerService.findByUsername(username);
        List<Genre> genres = writer.getGenres();

        for (FormField field : properties) {
            if (field.getId().equals("zanr")) {
                EnumFormType enumType = (EnumFormType) field.getType();
                enumType.getValues().clear();
                for (Genre g : genres) {
                    enumType.getValues().put(g.getName(), g.getName());
                }
            }
        }

        return new FormFieldDTO(task.getId(), pi.getId(), properties);
    }

    @PostMapping(path = "/publish-book/{taskId}", produces = "application/json")
    public ResponseEntity<?> publishBook(@RequestBody List<FormValuesDto> dto, @PathVariable String taskId) {

        HashMap<String, Object> map = this.mapListToDto(dto);

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        runtimeService.setVariable(processInstanceId, "book", map);

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

    @GetMapping(value = "/get-books")
    public List<FormFieldDTO> getBooks() {

        List<ProcessInstance> processInstances =
                runtimeService.createProcessInstanceQuery()
                        .processDefinitionKey("izdavanje_knjige")
                        .active()
                        .list();

        List<FormFieldDTO> ret = new ArrayList<>();

        for (ProcessInstance processInstance : processInstances) {
            Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0);

            if (task.getTaskDefinitionKey().equals("pisac_salje_rukopis")) {

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

    @PostMapping(value = "/upload-pdfs/{username}/{processId}")
    public ResponseEntity<?> upload_pdfs_for_book(@RequestPart("pdfs") List<MultipartFile> files, @PathVariable("username") String username, @PathVariable("processId") String processId) throws IOException {

        return writerService.upload_pdfs_for_book(files, username, processId);
    }

}
