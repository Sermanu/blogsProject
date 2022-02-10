package com.bootcamp.blogs.service;

import com.bootcamp.blogs.entity.Post;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PostService {

    ResponseEntity<List<Post>> findAll();

    ResponseEntity<Post> findbyId(Long id);

    ResponseEntity<Void> delete(Long id);

    ResponseEntity<Post> save (Post post);

    ResponseEntity<Post> update (Post post);

}
