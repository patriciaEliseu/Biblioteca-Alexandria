package com.example.alexandria.controller;


import com.example.alexandria.controller.dto.PublisherCreationDto;
import com.example.alexandria.controller.dto.PublisherDto;
import com.example.alexandria.entity.Publisher;
import com.example.alexandria.service.PublisherService;
import com.example.alexandria.service.exception.PublisherNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/publishers")
public class PublisherController {

    private final PublisherService publisherService;

    @Autowired
    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping("/{id}")
    public PublisherDto getPublisherByIdController(@PathVariable Long id) throws PublisherNotFoundException {
        return PublisherDto.fromEntity(publisherService.findByIdPublisherService(id));
    }

    @GetMapping
    public List<PublisherDto> getPublisherAllController() throws PublisherNotFoundException {
        List<Publisher> allPublishers = publisherService.findAllPublisherService();
        return allPublishers.stream().map(PublisherDto::fromEntity).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PublisherDto createPublisherController(@RequestBody PublisherCreationDto publisherCreationDto) {
        return PublisherDto.fromEntity(
                publisherService.createPublisherService(publisherCreationDto.toEntity())
        );
    }

    @PutMapping("/{id}")
    public PublisherDto updatePublisherController(@PathVariable Long id, @RequestBody PublisherCreationDto publisherCreationDto) throws  PublisherNotFoundException {
        return PublisherDto.fromEntity(
                publisherService.updatePublisherService(id, publisherCreationDto.toEntity())
        );
    }

    @DeleteMapping("/{id}")
public PublisherDto deletePublisherByIdController(@PathVariable Long id) throws PublisherNotFoundException {
        return PublisherDto.fromEntity(
                publisherService.deleteByIdPublisherService(id)
        );
    }


}
