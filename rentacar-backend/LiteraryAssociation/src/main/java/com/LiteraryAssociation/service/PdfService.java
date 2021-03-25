package com.LiteraryAssociation.service;

import com.LiteraryAssociation.model.Pdf;

import java.util.List;

public interface PdfService {

    Pdf save(Pdf pdf);

    List<Pdf> findAll();
}
