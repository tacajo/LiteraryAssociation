package com.LiteraryAssociation.repository;

import com.LiteraryAssociation.model.Notes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotesRepository extends JpaRepository<Notes, Long> {
}
