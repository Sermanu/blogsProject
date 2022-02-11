package com.bootcamp.blogs.service.impl;

import com.bootcamp.blogs.entity.Comment;
import com.bootcamp.blogs.entity.Post;
import com.bootcamp.blogs.repository.CommentRepository;
import com.bootcamp.blogs.repository.PostRepository;
import com.bootcamp.blogs.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public ResponseEntity<List<Comment>> findAll() {
        List<Comment> commentList = commentRepository.findAll();

        if (commentList.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(commentList, HttpStatus.OK);
        }

    }

    @Override
    public ResponseEntity<Comment> findById(Long id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);

        if (optionalComment.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(optionalComment.get(), HttpStatus.OK);
        }

    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        try {
            commentRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Comment> save(Comment comment) {
        try {

            Optional<Post> optionalPost = postRepository.findById(comment.getPost().getId());

            if (optionalPost.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {

                Post post = optionalPost.get();

                if (post.getStatus().equals("publicado")) {

                    Comment commentSaved = commentRepository.save(comment);
                    return new ResponseEntity<>(commentSaved, HttpStatus.ACCEPTED);

                } else {
                    return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
                }

            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Comment> update(Long id, Comment comment) {
        Optional<Comment> optionalComment = commentRepository.findById(id);

        if (optionalComment.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {

            Comment commentToUpdate = optionalComment.get();

            commentToUpdate.setName(comment.getName() != null
                    ? comment.getName()
                    : commentToUpdate.getName());
            commentToUpdate.setDate(comment.getDate() != null
                    ? comment.getDate()
                    : commentToUpdate.getDate());
            commentToUpdate.setEstado(comment.getEstado() != null
                    ? comment.getEstado()
                    : commentToUpdate.getEstado());
            commentToUpdate.setPost(comment.getPost() != null
                    ? comment.getPost()
                    : commentToUpdate.getPost());

            commentRepository.save(commentToUpdate);

            return new ResponseEntity<>(commentToUpdate, HttpStatus.OK);
        }
    }
}
