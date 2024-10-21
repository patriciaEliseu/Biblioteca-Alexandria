package com.example.alexandria.controller.dto;


import com.example.alexandria.entity.Author;

public record AuthorCreationDto(String name, String nationality) {
    public Author toEntity() {
        return new Author(name, nationality);
    }
}
