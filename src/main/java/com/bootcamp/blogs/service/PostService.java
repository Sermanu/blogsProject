package com.bootcamp.blogs.service;

import com.bootcamp.blogs.entity.Post;

import java.util.List;

public interface PostService {

    List<Post> findAll();

    Post findbyId(Long id);

    void delete(Long id);

    Post save (Post post);

    Post update (Post post);

}
