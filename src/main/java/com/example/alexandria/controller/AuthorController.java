package com.example.alexandria.controller;


import com.example.alexandria.controller.dto.AuthorCreationDto;
import com.example.alexandria.controller.dto.AuthorDto;
import com.example.alexandria.entity.Author;
import com.example.alexandria.service.AuthorService;
import com.example.alexandria.service.exception.AuthorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/authors")
public class AuthorController {
    //atributo
    private final AuthorService authorService;
    //construtor
    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }
    //pegar author pelo id
    @GetMapping("/{id}")
    public AuthorDto getAuthorById(@PathVariable Long id) throws AuthorNotFoundException {
        return AuthorDto.fromEntity(
                authorService.findByIdAuthorService(id)
        );
    }

    @GetMapping
    public List<AuthorDto> getAllAuthors() throws AuthorNotFoundException {
        List<Author> allAuthors = authorService.findAllAuthorService();
        return allAuthors.stream()
                .map(AuthorDto::fromEntity)
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDto createAuthor(@RequestBody AuthorCreationDto authorCreationDto) throws AuthorNotFoundException {
        return AuthorDto.fromEntity(
                authorService.createAuthorService(authorCreationDto.toEntity())
        );
    }

    @PutMapping("/{id}")
    public AuthorDto updateAuthor(@PathVariable Long id,
                                  @RequestBody AuthorCreationDto authorCreationDto) throws AuthorNotFoundException {
        return AuthorDto.fromEntity(
                authorService.updateAuthorService(id, authorCreationDto.toEntity())
        );
    }

    @DeleteMapping("/{id}")
    public AuthorDto deleteAuthorById(@PathVariable Long id) throws AuthorNotFoundException {
        return AuthorDto.fromEntity(
                authorService.deleteAuthorByIdService(id)
        );
    }
}
