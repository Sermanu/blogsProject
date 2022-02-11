package com.bootcamp.blogs.service.impl;

import com.bootcamp.blogs.entity.Blog;
import com.bootcamp.blogs.entity.Post;
import com.bootcamp.blogs.repository.PostRepository;
import com.bootcamp.blogs.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public ResponseEntity<List<Post>> findAll() {
        List<Post> postList = postRepository.findAll();

        if (postList.size() == 0 ) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(postList, HttpStatus.OK);
        }

    }

    @Override
    public ResponseEntity<Post> findbyId(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);

        if (optionalPost.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(optionalPost.get(), HttpStatus.OK);
        }

    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        try {
            postRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Post> save(Post post) {
        try {

            Blog blog = post.getBlog();

            List<Post> postsOfBlog = blog.getPostList();

            boolean canSavePost = true;

            for (Post postItem : postsOfBlog) {

                Date dateOfPostSaved = postItem.getDate();
                Date dateOfPostToSave = post.getDate();

                long diffInMillies = Math.abs(dateOfPostToSave.getTime() - dateOfPostSaved.getTime());
                long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

                if (diff < 1) {
                    canSavePost = false;
                }

            }

            if (canSavePost) {
                post.setStatus("borrador");
                Post postSaved = postRepository.save(post);
                return new ResponseEntity<>(postSaved, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Post> update(Long id, Post post) {
        Optional<Post> optionalPost = postRepository.findById(id);

        if (optionalPost.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {

            Post postToUpdate = optionalPost.get();

            postToUpdate.setTitle(post.getTitle() != null
                    ? post.getTitle()
                    : postToUpdate.getTitle());
            postToUpdate.setDate(post.getDate() != null
                    ? post.getDate()
                    : postToUpdate.getDate());
            postToUpdate.setStatus(post.getStatus() != null
                    ? post.getStatus()
                    : postToUpdate.getStatus());
            postToUpdate.setContent(post.getContent() != null
                    ? post.getContent()
                    : postToUpdate.getContent());
            postToUpdate.setBlog(post.getBlog() != null
                    ? post.getBlog()
                    : postToUpdate.getBlog());
            postToUpdate.setCommentList(post.getCommentList() != null
                    ? post.getCommentList()
                    : postToUpdate.getCommentList());

            postRepository.save(postToUpdate);
            return new ResponseEntity<>(postToUpdate, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Post> publishPost(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);

        if (optionalPost.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Post postFind =optionalPost.get();
            postFind.setStatus("publicado");
            postRepository.save(postFind);
            return new ResponseEntity<>(postFind, HttpStatus.OK);
        }
    }
}
