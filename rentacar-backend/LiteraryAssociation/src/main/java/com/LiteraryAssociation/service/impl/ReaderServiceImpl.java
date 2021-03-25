package com.LiteraryAssociation.service.impl;

import com.LiteraryAssociation.model.Reader;
import com.LiteraryAssociation.repository.ReaderRepository;
import com.LiteraryAssociation.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReaderServiceImpl implements ReaderService {

    @Autowired
    private ReaderRepository readerRepository;

    @Override
    public Reader save(Reader reader) {
        return readerRepository.save(reader);
    }

    @Override
    public Reader findByUsername(String username) {
        return readerRepository.findByUsername(username);
    }

    @Override
    public Reader findById(Long id) {
        return readerRepository.findById(id).get();
    }

    @Override
    public List<Reader> findAll() {
        return readerRepository.findAll();
    }

    @Override
    public List<Reader> findByBetaReaderTrue() {
        List<Reader> readers = readerRepository.findAll();
        List<Reader> ret = new ArrayList<>();
        for (Reader reader:readers) {
            if(reader.isBetaReader()) {
                ret.add(reader);
            }
        }
        return ret;
    }
}
