package com.example.alexandria.service;


import com.example.alexandria.entity.Book;
import com.example.alexandria.repository.BookRepository;
import com.example.alexandria.service.exception.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    //    atributo do tipo repository
    private  final BookRepository bookRepository;
    /*
    Este atributo vou receber neste constrututor como injecao
    de dependencia. Por isso está marcado com autowired e auto
    maticamente o Spring vai fazer a injecao de dependencia
    pra gente.
     */
    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
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
}
