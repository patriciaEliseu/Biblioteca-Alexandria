package com.example.alexandria.controller.dto;

import com.example.alexandria.entity.Publisher;

public record PublisherCreationDto(String name, String address) {

    public Publisher toEntity() {
        return new Publisher(name, address);
    }
}
