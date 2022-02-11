package com.bootcamp.blogs.controller;

import com.bootcamp.blogs.entity.Post;
import com.bootcamp.blogs.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    private ResponseEntity<List<Post>> findAllPosts () {
        return postService.findAll();
    }

    @PostMapping
    private ResponseEntity<Post> savePost (@RequestBody Post post) {
        return postService.save(post);
    }

    @GetMapping("/{id}")
    private ResponseEntity<Post> findPostById (@PathVariable Long id) {
        return postService.findbyId(id);
    }

    @DeleteMapping()
    private ResponseEntity<Void> deletePost (@PathVariable Long id) {
        return postService.delete(id);
    }

    @PatchMapping
    private ResponseEntity<Post> updatePost (@PathVariable Long id, @RequestBody Post post) {
        return postService.update(id, post);
    }

    @PostMapping("/publish/{id}")
    private ResponseEntity<Post> publishPost (@PathVariable Long id) {
        return postService.publishPost(id);
    }

}
