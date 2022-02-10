package com.bootcamp.blogs.service;

import com.bootcamp.blogs.entity.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> findAll();

    Comment findById (Long id);

    void delete(Long id);

    Comment save (Comment comment);

    Comment update (Comment comment);

}
