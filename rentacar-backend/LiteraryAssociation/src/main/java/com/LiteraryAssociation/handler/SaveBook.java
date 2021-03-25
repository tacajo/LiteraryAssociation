package com.LiteraryAssociation.handler;

import com.LiteraryAssociation.model.*;
import com.LiteraryAssociation.repository.EditorRepository;
import com.LiteraryAssociation.service.BookService;
import com.LiteraryAssociation.service.EditorService;
import com.LiteraryAssociation.service.GenreService;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class SaveBook implements JavaDelegate {

    @Autowired
    private GenreService genreService;

    @Autowired
    private BookService bookService;

    @Autowired
    private EditorService editorService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        HashMap<String, Object> book1 = (HashMap<String, Object>) delegateExecution.getVariable("book");

        Writer writer = (Writer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//        List<Book> books = bookService.findAll();
//        for(Book b : books){
//            if(b.getTitle().equals(book1.get("naslov").toString())){
//                throw new BpmnError("500","book_already_exists");
//            }
//        }

        Book book = new Book();
        book.setSynopsis(book1.get("sinopsis").toString());
        book.setTitle(book1.get("naslov").toString());
        Genre genre = genreService.findByName(book1.get("zanr").toString());
        book.setGenre(genre);
        book.setAccept(false);
        book.setOriginal(false);
        book.setWriter(writer);
        System.out.println(editorService.findOneById(1L));
        System.out.println(editorService.findOneById(6L));
        book = bookService.save(book);

        delegateExecution.setVariable("idKnjige", book.getId());
        delegateExecution.setVariable("naslov", book1.get("naslov").toString());
        delegateExecution.setVariable("sinopsis", book1.get("sinopsis").toString());
        delegateExecution.setVariable("pisac", writer.getFirstName() + " " + writer.getLastName());
        delegateExecution.setVariable("pisac_username", writer.getUsername());
        delegateExecution.setVariable("writerId", writer.getId());

    }
}
