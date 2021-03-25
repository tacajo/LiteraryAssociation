package com.LiteraryAssociation.service;

import com.LiteraryAssociation.model.Editor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface EditorService {

    Editor findOneById(Long id);

    Editor findByUsername(String username);

    List<Editor> findAll();

}
