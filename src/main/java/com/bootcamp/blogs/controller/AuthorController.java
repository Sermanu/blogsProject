package com.bootcamp.blogs.controller;

import com.bootcamp.blogs.entity.Author;
import com.bootcamp.blogs.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping()
    private ResponseEntity<List<Author>> findAllAuthors(){
        return authorService.findAll();
    }

    @PostMapping()
    private ResponseEntity<Author> saveAuthor(@RequestBody Author author){
        return authorService.save(author);
    }

    @GetMapping("/{id}")
    private ResponseEntity<Author> findAuthorById(@PathVariable Long id){
        return authorService.findById(id);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteAuthor(@PathVariable Long id){
        return authorService.delete(id);
    }

    @PatchMapping("/{id}")
    private ResponseEntity<Author> updateAuthor(@PathVariable Long id, @RequestBody Author author){
        return authorService.update(id, author);
    }

}
