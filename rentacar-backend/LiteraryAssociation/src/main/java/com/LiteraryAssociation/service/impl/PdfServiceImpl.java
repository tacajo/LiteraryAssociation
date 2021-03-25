package com.LiteraryAssociation.service.impl;

import com.LiteraryAssociation.model.Pdf;
import com.LiteraryAssociation.repository.PdfRepository;
import com.LiteraryAssociation.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PdfServiceImpl implements PdfService {

    @Autowired
    private PdfRepository pdfRepository;


    @Override
    public Pdf save(Pdf pdf) {
        return pdfRepository.save(pdf);
    }

    @Override
    public List<Pdf> findAll() {
        return pdfRepository.findAll();
    }
}
