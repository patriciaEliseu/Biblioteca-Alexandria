package com.example.alexandria.controller.dto;

import com.example.alexandria.entity.BookDetails;

public record BookDetailsDto(
        Long id,
        String summary,
        Integer pageCount,
        String year,
        String isbn) {

    public static BookDetailsDto fromEntity(BookDetails bookDetails) {
        return new BookDetailsDto(
                bookDetails.getId(),
                bookDetails.getSummary(),
                bookDetails.getPageCount(),
                bookDetails.getYear(),
                bookDetails.getIsbn()
        );
    }
}
