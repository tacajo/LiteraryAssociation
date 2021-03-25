package com.LiteraryAssociation.repository;

import com.LiteraryAssociation.model.Writer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WriterRepository extends JpaRepository<Writer, Long> {
    Writer findByUsername(String username);
}
