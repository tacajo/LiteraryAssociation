package com.LiteraryAssociation.repository;

import com.LiteraryAssociation.model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReaderRepository extends JpaRepository<Reader, Long> {

    Reader findByUsername(String username);

}
