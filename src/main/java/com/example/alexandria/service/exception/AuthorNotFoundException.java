package com.example.alexandria.service.exception;

public class AuthorNotFoundException extends NotFoundException {
    public AuthorNotFoundException() {
        super("Pessoa autora não encontrada!");
    }
}
