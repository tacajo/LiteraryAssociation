package com.LiteraryAssociation.service.impl;

import com.LiteraryAssociation.model.Book;
import com.LiteraryAssociation.model.Editor;
import com.LiteraryAssociation.model.Pdf;
import com.LiteraryAssociation.model.Writer;
import com.LiteraryAssociation.repository.EditorRepository;
import com.LiteraryAssociation.service.BookService;
import com.LiteraryAssociation.service.EditorService;
import com.LiteraryAssociation.service.PdfService;
import com.LiteraryAssociation.service.WriterService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
public class EditorServiceImpl implements EditorService {

    @Autowired
    private EditorRepository editorRepository;

    @Autowired
    private WriterService writerService;

    @Autowired
    TaskService taskService;

    @Autowired
    private PdfService pdfService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private BookService bookService;

    @Override
    public Editor findOneById(Long id) {
        return editorRepository.findOneById(id);
    }

    @Override
    public Editor findByUsername(String username) {
        return editorRepository.findByUsername(username);
    }

    @Override
    public List<Editor> findAll() {return  editorRepository.findAll();}

}
