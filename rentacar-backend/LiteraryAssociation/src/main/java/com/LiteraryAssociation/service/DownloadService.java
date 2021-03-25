package com.LiteraryAssociation.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface DownloadService {

    ResponseEntity<byte[]> downloadFile(String processId) throws Exception;

    ResponseEntity<?> downloadFile(String processId, String title) throws Exception;
}
