package com.example.alexandria.service.exception;

public class BookDetailsNotFoundException extends NotFoundException {
  public BookDetailsNotFoundException() {
    super("Detalhes de livro não encontrados!");
  }
}
