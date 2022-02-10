package com.bootcamp.blogs.controller;

import com.bootcamp.blogs.entity.Comment;
import com.bootcamp.blogs.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping
    private ResponseEntity<List<Comment>> getAllComments () {
        return commentService.findAll();
    }

    @PostMapping
    private ResponseEntity<Comment> saveComment (@RequestBody Comment comment) {
        return commentService.save(comment);
    }

    @GetMapping("/{id}")
    private ResponseEntity<Comment> getCommentById (@PathVariable Long id) {
        return commentService.findById(id);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteComment (@PathVariable Long id) {
        return commentService.delete(id);
    }

    @PatchMapping("/{id}")
    private ResponseEntity<Comment> updateComment (@PathVariable Long id, @RequestBody Comment comment) {
        return commentService.update(id, comment);
    }

}
