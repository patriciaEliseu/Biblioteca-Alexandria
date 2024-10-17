package com.example.alexandria.service;


import com.example.alexandria.entity.Author;
import com.example.alexandria.repository.AuthorRepository;
import com.example.alexandria.service.exception.AuthorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    public final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author findByIdAuthorService(Long id) throws AuthorNotFoundException {
        return authorRepository.findById(id)
                .orElseThrow(AuthorNotFoundException::new);
    }
    public List<Author> findAllAuthorService() throws AuthorNotFoundException {
        return authorRepository.findAll();
    }

    public Author createAuthorService(Author author) throws AuthorNotFoundException {
        return authorRepository.save(author);
    }

    public Author updateAuthorService(Long id, Author author) throws AuthorNotFoundException {
        Author authorFromDB = findByIdAuthorService(id);
        authorFromDB.setName(author.getName());
        authorFromDB.setNationality(author.getNationality());

        return authorRepository.save(author);

    }

    public Author deleteAuthorByIdService(Long id) throws AuthorNotFoundException {
        Author author = findByIdAuthorService(id);

        authorRepository.deleteById(id);

        return author;

    }



}
