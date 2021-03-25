package com.LiteraryAssociation.service.impl;

import com.LiteraryAssociation.dto.FormFieldDTO;
import com.LiteraryAssociation.model.Book;
import com.LiteraryAssociation.model.CommitteeMember;
import com.LiteraryAssociation.model.Pdf;
import com.LiteraryAssociation.model.Writer;
import com.LiteraryAssociation.repository.PdfRepository;
import com.LiteraryAssociation.repository.WriterRepository;
import com.LiteraryAssociation.service.*;
import com.LiteraryAssociation.service.CommitteeMemberService;
import com.LiteraryAssociation.service.EmailService;
import com.LiteraryAssociation.service.WriterService;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
public class WriterServiceImpl implements WriterService {

    @Autowired
    private PdfRepository pdfRepository;

    @Autowired
    private WriterRepository writerRepository;

    @Autowired
    private CommitteeMemberService committeeMemberService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private PdfService pdfService;

    @Autowired
    private BookService bookService;

    public Writer save(Writer writer) {
        return writerRepository.save(writer);
    }

    public Writer findByUsername(String username) {
        return writerRepository.findByUsername(username);
    }

    @Override
    public Writer findById(Long id) {
        return writerRepository.findById(id).get();
    }

    @Override
    public List<Writer> findAll() {
        return writerRepository.findAll();
    }

    @Override
    public ResponseEntity<?> upload_pdfs(List<MultipartFile> files, String username, String taskId) throws IOException {

        Writer writer = writerRepository.findByUsername(username);
        List<Pdf> pdfs = new ArrayList<>();
        for (MultipartFile file : files) {
            Pdf pdf = new Pdf();
            pdf.setName(file.getOriginalFilename());
            pdf.setType(file.getContentType());
            pdf.setPicByte(compressBytes(file.getBytes()));
            pdf.setWriter(writer);
            pdfRepository.save(pdf);

            pdfs.add(pdf);
        }

        writer.setPdfs(pdfs);
        writer.setWaitingForVotes(true);
        writerRepository.save(writer);

        List<CommitteeMember> cms = committeeMemberService.findAll();
        for (CommitteeMember c : cms) {
            emailService.sendPdfsToCommitteeMembers(files, writer, c);
        }
        System.out.println("Zavrsio se task: " + taskService.createTaskQuery().taskId(taskId).singleResult().getTaskDefinitionKey());

        taskService.complete(taskId);

        return new ResponseEntity<>("sacuvan pdf", HttpStatus.OK);
    }

    @Override
    public void payPee() {
        Writer writer = (Writer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        writer.setMembershipFeePaid(true);
        writerRepository.save(writer);
    }

    @Override
    public boolean checkWriter(Long id) {
        Writer writer = (Writer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Writer writerDB1 = writerRepository.findByUsername(writer.getUsername());
        Writer writerDB2 = writerRepository.findById(id).get();

        return writerDB1.getId() == writerDB2.getId();
    }

    @Override
    public boolean isPaid() {
        Writer writer = (Writer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Writer writerDB = writerRepository.findById(writer.getId()).get();
        return writerDB.getMembershipFeePaid();
    }

    @Override
    public void member(Long id) {
        Writer writer = (Writer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (writer.getId() == id) {
            writer.setMember(true);
            writerRepository.save(writer);
        }

    }

    @Override
    public String findTaskUploadPds() {
        String taskId = "";
        String processId = "";

        List<ProcessInstance> processInstances =
                runtimeService.createProcessInstanceQuery()
                        .processDefinitionKey("upp_process_1")
                        .active() // we only want the unsuspended process instances
                        .list();
        Writer writer = (Writer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Writer writerDB = writerRepository.findByUsername(writer.getUsername());
        for (ProcessInstance processInstance : processInstances) {
            if(taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().size() > 0) {
                if (writerDB.getId() == runtimeService.getVariable(processInstance.getId(), "writerId")) {
                    Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0);
                    if (task.getTaskDefinitionKey().equals("dostavljanje_jos_materijala") ||
                            task.getTaskDefinitionKey().equals("dodavanje_pdfa")) {
                        taskId = task.getId();
                        processId = processInstance.getId();
                    }
                }
            }
        }
        runtimeService.setVariable(processId, "numOfSending",
                (Integer) runtimeService.getVariable(processId, "numOfSending") + 1);
        runtimeService.setVariable(processId, "ispostovanRokDostavljanje", true);
        return taskId;
    }

    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
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

    @Override
    public ResponseEntity<?> upload_pdfs_for_book(List<MultipartFile> files, String username, String processId) throws IOException {

        Writer writer = writerRepository.findByUsername(username);
        Long idBook = (Long) runtimeService.getVariable(processId, "idKnjige");
        Book book = bookService.findOneById(idBook);


        List<Pdf> pdfs = new ArrayList<>();

        for(MultipartFile file : files){
            Pdf pdf = new Pdf();
            pdf.setName(file.getOriginalFilename());
            pdf.setType(file.getContentType());
            pdf.setPicByte(compressBytes(file.getBytes()));
            pdf.setWriter(writer);
            pdf.setBook(book);
            pdfService.save(pdf);

            pdfs.add(pdf);
        }
        writer.setPdfs(pdfs);
        writerRepository.save(writer);

        if(book.getPdfs().size() == 0) {
            runtimeService.setVariable(processId, "izmeneUradjenje", true);
        }
        book.setPdfs(pdfs);
        bookService.save(book);


        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0);
        taskService.complete(task.getId());

        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
}
