package com.bootcamp.blogs.service;

import com.bootcamp.blogs.entity.Blog;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BlogService {

    ResponseEntity<List<Blog>> findAll();

    ResponseEntity<Blog> findById(Long id);

    ResponseEntity<Void> delete(Long id);

    ResponseEntity<Blog> save(Blog blog);

    ResponseEntity<Blog> update (Long id, Blog blog);

    ResponseEntity<Blog> activateBlog(Long id);

}
