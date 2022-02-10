package com.bootcamp.blogs.service;

import com.bootcamp.blogs.entity.Comment;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommentService {

    ResponseEntity<List<Comment>> findAll();

    ResponseEntity<Comment> findById (Long id);

    ResponseEntity<Void> delete(Long id);

    ResponseEntity<Comment> save (Comment comment);

    ResponseEntity<Comment> update (Comment comment);

}
