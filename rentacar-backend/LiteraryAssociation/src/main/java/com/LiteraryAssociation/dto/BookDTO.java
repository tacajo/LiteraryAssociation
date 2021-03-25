package com.LiteraryAssociation.dto;

import com.LiteraryAssociation.model.Writer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    private Long id;

    private String title;

    private String synopsis;

    private byte[] bytes;

    private WriterDTO writer;

    private Long isbn;

    private Long year;

    private String city;

    private Long pageNumber;

    private Double price;

    public BookDTO(String title, String synopsis, byte[] bytes) {
        this.title = title;
        this.synopsis = synopsis;
        this.bytes = bytes;
    }
}
