package com.LiteraryAssociation.repository;

import com.LiteraryAssociation.model.Editor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EditorRepository extends JpaRepository<Editor, Long> {

    Editor findOneById(Long id);

    Editor findByUsername(String username);
}
