package com.example.alexandria.controller;


import com.example.alexandria.controller.dto.BookCreationDto;
import com.example.alexandria.controller.dto.BookDetailsCreationDto;
import com.example.alexandria.controller.dto.BookDetailsDto;
import com.example.alexandria.controller.dto.BookDto;
import com.example.alexandria.entity.Book;
import com.example.alexandria.service.BookService;
import com.example.alexandria.service.exception.AuthorNotFoundException;
import com.example.alexandria.service.exception.BookDetailsNotFoundException;
import com.example.alexandria.service.exception.BookNotFoundException;
import com.example.alexandria.service.exception.PublisherNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/books")
public class BookController {
    private  final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/{id}")
    public BookDto getBookByIdController(@PathVariable Long id) throws BookNotFoundException {
        return BookDto.fromEntity(bookService.findByIdBookService(id));
    }
    // Qdo chamamos a liste sem paginação
    @GetMapping
    public List<BookDto> getAllBooksController() {
        List<Book> allBooks = bookService.findAllBookService();
        return allBooks.stream()
                .map(BookDto::fromEntity).toList();
    }
    //Qdo chamamos a lista de books com paginas
    //http://localhost:8080/books?pageNumber=0&pageSize=10
    @GetMapping
    public List<BookDto> getAllBooks(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "20") int pageSize
    ) {
        List<Book> paginatedBooks = bookService.findAll(pageNumber, pageSize);
        return paginatedBooks.stream()
                .map(BookDto::fromEntity)
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto createBookController(@RequestBody BookCreationDto bookCreationDto) {
        return BookDto.fromEntity(
                bookService.createBookService(bookCreationDto.toEntity())
        );
    }

    @PutMapping("/{id}")
    public BookDto updateBookController(@PathVariable Long id, @RequestBody BookCreationDto bookCreationDto) throws  BookNotFoundException {
        return BookDto.fromEntity(
                bookService.updateBookService(id, bookCreationDto.toEntity())
        );
    }

    @DeleteMapping("/{id}")
    public BookDto deleteBookController(@PathVariable Long id) throws BookNotFoundException {
        return BookDto.fromEntity(
                bookService.deleteByIdBookService(id)
        );
    }

    @PostMapping("/{bookId}/details")
    @ResponseStatus(HttpStatus.CREATED)
    public BookDetailsDto createBookDetailsController(@PathVariable Long bookId, @RequestBody BookDetailsCreationDto bookDetailsCreationDto) throws  BookNotFoundException {
        return BookDetailsDto.fromEntity(
                bookService.createBookDetail(bookId, bookDetailsCreationDto.toEntity())
        );
    }

    @GetMapping("/{bookId}/detail")
    public BookDetailsDto getBookDetail(@PathVariable Long bookId)
            throws BookNotFoundException, BookDetailsNotFoundException {
        return BookDetailsDto.fromEntity(
                bookService.getBookDetail(bookId)
        );
    }

    @PutMapping("/{bookId}/detail")
    public BookDetailsDto updateBookDetail(@PathVariable Long bookId,
                                          @RequestBody BookDetailsCreationDto bookDetailCreationDto)
            throws BookDetailsNotFoundException, BookNotFoundException {
        return BookDetailsDto.fromEntity(
                bookService.updateBookDetail(bookId, bookDetailCreationDto.toEntity())
        );
    }

    @DeleteMapping("/{bookId}/detail")
    public BookDetailsDto removeBookDetail(@PathVariable Long bookId)
            throws BookDetailsNotFoundException, BookNotFoundException {
        return BookDetailsDto.fromEntity(
                bookService.removeBookDetail(bookId)
        );
    }

    @PutMapping("/{bookId}/publisher/{publisherId}")
    public BookDto setBookPublisher(@PathVariable Long bookId,
                                    @PathVariable Long publisherId) throws BookNotFoundException, PublisherNotFoundException {
        return BookDto.fromEntity(
                bookService.setBookPublisher(bookId, publisherId)
        );
    }

    @DeleteMapping("/{bookId}/publisher")
    public BookDto removeBookPublisher(@PathVariable Long bookId) throws BookNotFoundException {
        return BookDto.fromEntity(
                bookService.removeBookPublisher(bookId)
        );
    }

    @PutMapping("/{bookId}/authors/{authorId}")
    public BookDto addBookAuthor(@PathVariable Long bookId,
                                 @PathVariable Long authorId) throws AuthorNotFoundException, BookNotFoundException {
        return BookDto.fromEntity(
                bookService.addBookAuthor(bookId, authorId)
        );
    }

    @DeleteMapping("/{bookId}/authors/{authorId}")
    public BookDto removeBookAuthor(@PathVariable Long bookId,
                                    @PathVariable Long authorId) throws AuthorNotFoundException, BookNotFoundException {
        return BookDto.fromEntity(
                bookService.removeBookAuthor(bookId, authorId)
        );
    }

}
