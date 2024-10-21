package com.example.alexandria.controller.dto;

import com.example.alexandria.entity.Publisher;

public record PublisherDto(Long id, String name, String address) {

    public static PublisherDto fromEntity(Publisher publisher) {
        return new PublisherDto(
                publisher.getId(),
                publisher.getName(),
                publisher.getAddress()
        );
    }
}
