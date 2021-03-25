package com.LiteraryAssociation.service;

import com.LiteraryAssociation.dto.FormFieldDTO;
import com.LiteraryAssociation.model.Writer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface WriterService {

    Writer save(Writer writer);

    Writer findByUsername(String username);

    Writer findById(Long id);

    List<Writer> findAll();

    ResponseEntity<?> upload_pdfs(List<MultipartFile> files, String username, String taskId) throws IOException;

    ResponseEntity<?> upload_pdfs_for_book(List<MultipartFile> files, String username, String processId) throws IOException;

    void payPee();

    boolean checkWriter(Long id);

    boolean isPaid();

    void member(Long id);

    String findTaskUploadPds();

}
