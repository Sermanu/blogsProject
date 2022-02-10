package com.bootcamp.blogs.service;

import com.bootcamp.blogs.entity.Author;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    ResponseEntity<List<Author>> findAll();

    ResponseEntity<Author> findById(Long id);

    ResponseEntity<Author> save(Author author);

    ResponseEntity<Void> delete(Long id);

    ResponseEntity<Author> update(Long id, Author author);

}
