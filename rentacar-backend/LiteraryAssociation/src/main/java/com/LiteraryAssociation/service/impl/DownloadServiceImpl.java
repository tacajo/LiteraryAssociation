package com.LiteraryAssociation.service.impl;

import com.LiteraryAssociation.dto.BookDTO;
import com.LiteraryAssociation.model.Book;
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
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

@Service
public class DownloadServiceImpl implements DownloadService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private BookService bookService;

    @Autowired
    private TaskService taskService;

    @Override
    public ResponseEntity<byte[]> downloadFile(String processId) throws Exception {
        Long idBook = (Long) runtimeService.getVariable(processId, "idKnjige");
        Book book = bookService.findOneById(idBook);
        BookDTO bookDTO = new BookDTO(book.getTitle(), book.getSynopsis(), book.getPdfs().get(0).getPicByte());

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(bookDTO);
        byte[] isr = decompressBytes(book.getPdfs().get(0).getPicByte());
        String fileName = book.getTitle() + ".pdf";
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentLength(isr.length);
        respHeaders.setContentType(MediaType.APPLICATION_PDF);
        respHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        respHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);

        Task task = taskService.createTaskQuery().processInstanceId(processId).list().get(0);
        if(task.getTaskDefinitionKey().equals("Urednik_downloaduje_i_cita_rad")){
            taskService.complete(task.getId());
        }

        return new ResponseEntity<byte[]>(isr, respHeaders, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> downloadFile(String processId, String title) throws Exception {
        Task task = taskService.createTaskQuery().processInstanceId(processId).list().get(0);
        Long editor = Long.parseLong(task.getAssignee());

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!user.getId().equals(editor)){
            throw new BpmnError("ERROR_500","Nema pristup za skidanje knjige");
        }

        Book book = bookService.findByTitle(title);
        BookDTO bookDTO = new BookDTO(book.getTitle(), book.getSynopsis(), book.getPdfs().get(0).getPicByte());

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(bookDTO);
        byte[] isr = decompressBytes(book.getPdfs().get(0).getPicByte());
        String fileName = book.getTitle() + ".pdf";
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentLength(isr.length);
        respHeaders.setContentType(MediaType.APPLICATION_PDF);
        respHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        respHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);

        return new ResponseEntity<byte[]>(isr, respHeaders, HttpStatus.OK);
    }

    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }
}
