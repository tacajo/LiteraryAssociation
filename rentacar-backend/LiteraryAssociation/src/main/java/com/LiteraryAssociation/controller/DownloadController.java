package com.LiteraryAssociation.controller;

import com.LiteraryAssociation.dto.BookDTO;
import com.LiteraryAssociation.model.Book;
import com.LiteraryAssociation.model.Pdf;
import com.LiteraryAssociation.model.User;
import com.LiteraryAssociation.service.BookService;
import com.LiteraryAssociation.service.DownloadService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

@CrossOrigin(origins = "https://localhost:4200")
@RestController
@RequestMapping(value = "/download", produces = MediaType.APPLICATION_JSON_VALUE)
public class DownloadController {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private BookService bookService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private DownloadService downloadService;

    @GetMapping(value = "/{processId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable("processId") String processId) throws Exception {

        return downloadService.downloadFile(processId);
    }

    @GetMapping(value = "/{processId}/{title}")
    public ResponseEntity<?> downloadFile(@PathVariable("processId") String processId, @PathVariable("title") String title) throws Exception {

        return downloadService.downloadFile(processId, title);
    }

    @GetMapping(value = "/compliteDownload/{processId}")
    public ResponseEntity<?> compliteTask(@PathVariable("processId") String processId){

        Task task = taskService.createTaskQuery().processInstanceId(processId).list().get(0);
        if(task.getTaskDefinitionKey().equals("urednik_skida_knjige")){
            taskService.complete(task.getId());
        }

        return new ResponseEntity("ok", HttpStatus.OK);

    }

}
