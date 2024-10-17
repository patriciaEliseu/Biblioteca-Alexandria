package com.example.alexandria.controller.dto;

import com.example.alexandria.entity.Book;

public record BookCreationDto(String title, String genre) {

    public Book toEntity() {
        return new Book(title, genre);
    }
}
