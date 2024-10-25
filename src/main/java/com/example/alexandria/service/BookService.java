package com.example.alexandria.service;


import com.example.alexandria.entity.Author;
import com.example.alexandria.entity.Book;
import com.example.alexandria.entity.BookDetails;
import com.example.alexandria.entity.Publisher;
import com.example.alexandria.repository.BookDetailRepository;
import com.example.alexandria.repository.BookRepository;
import com.example.alexandria.service.exception.AuthorNotFoundException;
import com.example.alexandria.service.exception.BookDetailsNotFoundException;
import com.example.alexandria.service.exception.BookNotFoundException;
import com.example.alexandria.service.exception.PublisherNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    //    atributo do tipo repository
    private final BookRepository bookRepository;
    private final BookDetailRepository bookDetailRepository;
    private final PublisherService publisherService;
    private final AuthorService authorService;



    /*
    Este atributo vou receber neste constrututor como injecao
    de dependencia. Por isso está marcado com autowired e auto
    maticamente o Spring vai fazer a injecao de dependencia
    pra gente.
     */
    @Autowired
    public BookService(BookRepository bookRepository, BookDetailRepository bookDetailRepository, PublisherService publisherService, AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.bookDetailRepository = bookDetailRepository;
        this.publisherService = publisherService;
        this.authorService = authorService;
    }
    /*
    Agora posso implementar métodos aqui na service.
    No metodo abaixo ele será responsavel para pegar
    todos os livros que estao no banco de dados
     */

    public List<Book> findAllBookService() {
        return bookRepository.findAll();
    }

    public Book findByIdBookService(Long id) throws BookNotFoundException {
        return bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
    }

//    metodo para criar um novo livro.

    public Book createBookService(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBookService(Long id, Book book) throws BookNotFoundException {
//        com o metodo findById verifico se existe o livro
        Book bookFromDb = findByIdBookService(id);
        /*
        Se tiver o id altero o titulo e genero do livro
        atraves do param book
         */
        bookFromDb.setTitle(book.getTitle());
        bookFromDb.setGenre(book.getGenre());
        /*
        Depois eu mando salvar o livro com as
        novas informacoes atraves do repositorio.
         */
        return bookRepository.save(bookFromDb);
    }

    public Book deleteByIdBookService(Long id) throws BookNotFoundException {
        /*
        pegamos a entidade antes de apagar,
        para poder retorna-la. Usamos o metodo
        findById(id);
         */
        Book book = findByIdBookService(id);
        bookRepository.deleteById(id);
        return book;
    }

    public BookDetails createBookDetail(Long bookId, BookDetails bookDetail)
            throws BookNotFoundException {
        Book book = findByIdBookService (bookId);

        bookDetail.setBook(book);
        return bookDetailRepository.save(bookDetail);
    }

    public BookDetails getBookDetail(Long bookId)
            throws BookNotFoundException, BookDetailsNotFoundException {
        Book book = findByIdBookService(bookId);

        BookDetails bookDetailFromDb = book.getDetails();

        if (bookDetailFromDb == null) {
            throw new BookDetailsNotFoundException();
        }

        return bookDetailFromDb;
    }
    public BookDetails updateBookDetail(Long bookId, BookDetails bookDetail)
            throws BookNotFoundException, BookDetailsNotFoundException {
        BookDetails bookDetailFromDb = getBookDetail(bookId);

        bookDetailFromDb.setSummary(bookDetail.getSummary());
        bookDetailFromDb.setPageCount(bookDetail.getPageCount());
        bookDetailFromDb.setYear(bookDetail.getYear());
        bookDetailFromDb.setIsbn(bookDetail.getIsbn());

        return bookDetailRepository.save(bookDetailFromDb);
    }

    public BookDetails removeBookDetail(Long bookId)
            throws BookNotFoundException, BookDetailsNotFoundException {
        Book book = findByIdBookService(bookId);
        BookDetails bookDetail = book.getDetails();

        if (bookDetail == null) {
            throw new BookDetailsNotFoundException();
        }

        book.setDetails(null);
        bookDetail.setBook(null);

        bookDetailRepository.delete(bookDetail);

        return bookDetail;
    }

    public Book setBookPublisher(Long bookId, Long publisherId) throws BookNotFoundException, PublisherNotFoundException {
        Book book = findByIdBookService(bookId);
        Publisher publisher = publisherService.findByIdPublisherService(publisherId);

        book.setPublisher(publisher);
        return bookRepository.save(book);
    }

    public Book removeBookPublisher(Long bookId) throws BookNotFoundException {
        Book book = findByIdBookService(bookId);

        book.setPublisher(null);

        return bookRepository.save(book);
    }

    public Book addBookAuthor(Long bookId, Long authorId)
            throws BookNotFoundException, AuthorNotFoundException {
        Book book = findByIdBookService (bookId);
        Author author = authorService.findByIdAuthorService (authorId);

        book.getAuthors().add(author);

        return bookRepository.save(book);
    }

    public Book removeBookAuthor(Long bookId, Long authorId)
            throws BookNotFoundException, AuthorNotFoundException {
        Book book = findByIdBookService(bookId);
        Author author = authorService.findByIdAuthorService(authorId);

        book.getAuthors().remove(author);

        return bookRepository.save(book);
    }

    public List<Book> findAll(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Book> page = bookRepository.findAll(pageable);

        return page.toList();
    }
}
