package com.bootcamp.blogs.controller;

import com.bootcamp.blogs.entity.Blog;
import com.bootcamp.blogs.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("blogs")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping
    private ResponseEntity<List<Blog>> finAllBlogs() {
        return blogService.findAll();
    }

    @PostMapping
    private ResponseEntity<Blog> saveBlog(@RequestBody Blog blog){
        return blogService.save(blog);
    }

    @GetMapping("/{id}")
    private ResponseEntity<Blog> findBlogById(@PathVariable Long id){
        return blogService.findById(id);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteBlog(@PathVariable Long id){
        return blogService.delete(id);
    }

    @PatchMapping("/{id}")
    private ResponseEntity<Blog> updateBlog (@PathVariable Long id, @RequestBody Blog blog){
        return blogService.update(id, blog);
    }

    @PostMapping("/activate/{id}")
    private ResponseEntity<Blog> activateBlog (@PathVariable Long id) {
        return blogService.activateBlog(id);
    }
}
