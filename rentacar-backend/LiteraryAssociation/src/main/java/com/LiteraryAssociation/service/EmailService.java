package com.LiteraryAssociation.service;


import com.LiteraryAssociation.model.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EmailService {

    @Async
    void sendPdfsToCommitteeMembers(List<MultipartFile> files, Writer writer, CommitteeMember cms);

    @Async
    void sendEmailToEditor(Editor editor, Book book);

    @Async
    void sendEmailForAccept(Writer writer, Book book);

    @Async
    void sendEmailForDecline(Writer writer, Book book, String explanation);

    @Async
    void sendEmailForBookTermination(Writer writer, Book book);

    @Async
    void sendMessage(String message, User user);

    @Async
    void sendEmailToEditorAboutPlagiarism(Editor editor, Book book);

    @Async
    void sendManuscript(Book book, Editor editor);
}
