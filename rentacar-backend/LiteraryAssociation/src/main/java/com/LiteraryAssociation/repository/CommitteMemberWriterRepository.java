package com.LiteraryAssociation.repository;

import com.LiteraryAssociation.enums.Opinion;
import com.LiteraryAssociation.model.CommitteMemberWriter;
import com.LiteraryAssociation.model.Writer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommitteMemberWriterRepository extends JpaRepository<CommitteMemberWriter, Long> {

    List<CommitteMemberWriter> findByWriter(Writer writer);

    List<CommitteMemberWriter> findByOpinionAndWriter(Opinion opinion, Writer writer);

    List<CommitteMemberWriter> findByWriterAndNumOfSending(Writer writer, Integer numOfSending);

    List<CommitteMemberWriter> findByOpinionAndWriterAndNumOfSending(Opinion opinion, Writer writer, Integer numOfSending);
}
