package com.LiteraryAssociation.service.impl;

import com.LiteraryAssociation.model.Book;
import com.LiteraryAssociation.model.CommitteeMember;
import com.LiteraryAssociation.model.Pdf;
import com.LiteraryAssociation.model.User;
import com.LiteraryAssociation.model.Editor;
import com.LiteraryAssociation.model.Writer;
import com.LiteraryAssociation.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public JavaMailSender emailSender;

    @Autowired
    private Environment env;

    @Override
    public void sendPdfsToCommitteeMembers(List<MultipartFile> files, Writer writer, CommitteeMember c) {

        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(env.getProperty("spring.mail.username"));
            helper.setTo(c.getEmail());
            helper.setSubject("Pdfs");
            for(MultipartFile file : files){
                byte[] bytes = file.getBytes();
                helper.addAttachment(file.getOriginalFilename(), new ByteArrayResource(bytes));
            }
            helper.setText("You received pdfs from " + writer.getFirstName() + " " + writer.getLastName() + " (" + writer.getUsername() + ").", true);
            emailSender.send(message);
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(String message, User user) {
        System.out.println(user);
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setSubject("Information");
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setText("Mr/Mrs "  + user.getFirstName() +  ",\n\n"+ message +  ".\n\n" );
        try{
            emailSender.send(mail);
            logger.info("javaMailSender.send(mail) poslao mejl");
        }
        catch( Exception e ){
            e.printStackTrace();
            System.out.println("javaMailSender.send(mail) nije prosao" );
        }
    }

    @Override
    public void sendEmailToEditor(Editor editor, Book book) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(editor.getEmail());
            message.setSubject("Prijava nove knjige u sistem");
            message.setText("Postovani/a, " + editor.getFirstName() + " " + editor.getLastName() + "\n\n" +
                    "U sistem je prijavljen nova knjiga pod nazivom " + book.getTitle() + ".\n\n Srdacno,\n Literarno udruzenje");
            emailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendEmailForAccept(Writer writer, Book book) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(writer.getEmail());
            message.setSubject("Vasa knjiga je prihvacena");
            message.setText("Postovani/a, " + writer.getFirstName() + " " + writer.getLastName() + "\n\n" +
                    "Knjiga koju ste prijavili pod nazivom " + book.getTitle() + " je prihvacena. Neophodno je da dostavite citav rukopis." + "\n\n Srdacno,\n Literarno udruzenje");
            emailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
  
    @Override
    public void sendEmailToEditorAboutPlagiarism(Editor editor, Book book) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(editor.getEmail());
            message.setSubject("Prijava plagijarizma");
            message.setText("Postovani/a, " + editor.getFirstName() + " " + editor.getLastName() + "\n\n" +
                    "Prijavljeno je da je sledeca knjiga plagijarizam : " + book.getTitle() + ".\n\n Molimo pokrenite postupak provjere plagijarizma. \n\n Srdacno,\n Literarno udruzenje");
            emailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void  sendManuscript(Book book, Editor editor) {

        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(env.getProperty("spring.mail.username"));
            helper.setTo(editor.getEmail());
            helper.setSubject("Pdf " + " from " + editor.getFirstName() + " " + editor.getLastName());

            byte[] bytes = book.getPdfs().get(0).getPicByte();
            helper.addAttachment(book.getPdfs().get(0).getName(), new ByteArrayResource(bytes));

            helper.setText("You received pdf from " + editor.getFirstName() + " " + editor.getLastName() + ".", true);
            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void sendEmailForDecline(Writer writer, Book book, String explanation) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(writer.getEmail());
            message.setSubject("Vasa knjiga je odbijena");
            message.setText("Postovani/a, " + writer.getFirstName() + " " + writer.getLastName() + "\n\n" +
                    "Vasa knjiga pod nazivom " + book.getTitle() + " je odbijena. Razlog: " + explanation + ".\n\n Srdacno,\n Literarno udruzenje");
            emailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void sendEmailForBookTermination(Writer writer, Book book) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(writer.getEmail());
            message.setSubject("Otkazano objavljivanje knjige");
            message.setText("Postovani/a, " + writer.getFirstName() + " " + writer.getLastName() + "\n\n" +
                    "Niste poslali rukopis za knjigu pod nazivom " + book.getTitle() + " u zadatom roku." + "\n\n Srdacno,\n Literarno udruzenje");
            emailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
