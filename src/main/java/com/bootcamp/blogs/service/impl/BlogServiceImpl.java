package com.bootcamp.blogs.service.impl;

import com.bootcamp.blogs.entity.Author;
import com.bootcamp.blogs.entity.Blog;
import com.bootcamp.blogs.repository.BlogRepository;
import com.bootcamp.blogs.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public ResponseEntity<List<Blog>> findAll() {
        List<Blog> blogList = blogRepository.findAll();

        if (blogList.size() == 0) {
            return new ResponseEntity<List<Blog>>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Blog>>(blogList, HttpStatus.OK);
        }

    }

    @Override
    public ResponseEntity<Blog> findById(Long id) {
        Optional<Blog> blogFind = blogRepository.findById(id);

        if (blogFind.isEmpty()) {
            return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<Blog>(blogFind.get(), HttpStatus.OK);
        }

    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        try {
            blogRepository.deleteById(id);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Blog> save(Blog blog) {
        try {

            Author blogAuthor = blog.getAuthor();

            if (blogAuthor.getBlogList().size() >= 3) {
                return new ResponseEntity<Blog>(HttpStatus.PRECONDITION_FAILED);
            }

            Date actualDate = new Date();
            Date birthDateAuthor = blogAuthor.getBirthDate();

            long diffInMillies = Math.abs(actualDate.getTime() - birthDateAuthor.getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

            if (diff < 18) {
                return new ResponseEntity<Blog>(HttpStatus.PRECONDITION_REQUIRED);
            }

            blog.setStatus("inactivo");
            Blog blogSaved = blogRepository.save(blog);
            return new ResponseEntity<Blog>(blogSaved, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<Blog>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Blog> update(Long id, Blog blog) {
        Optional<Blog> optionalBlogFind = blogRepository.findById(id);

        if (optionalBlogFind.isEmpty()) {
            return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
        } else {
            Blog blogFind = optionalBlogFind.get();

            blogFind.setName(blog.getName() != null ? blog.getName() : blogFind.getName());
            blogFind.setDescription(blog.getDescription() != null ? blog.getDescription() : blogFind.getDescription());
            blogFind.setUrl(blog.getUrl() != null ? blog.getUrl() : blogFind.getUrl());
            blogFind.setAuthor(blog.getAuthor() != null ? blog.getAuthor() : blogFind.getAuthor());
            blogFind.setPostList(blog.getPostList() != null ? blog.getPostList() : blogFind.getPostList());
            blogFind.setStatus(blog.getStatus() != null ? blog.getStatus() : blogFind.getStatus());

            blogRepository.save(blogFind);
            return new ResponseEntity<Blog>(blogFind, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Blog> activateBlog(Long id) {
        Optional<Blog> optionalBlogFind = blogRepository.findById(id);

        if (optionalBlogFind.isEmpty()) {
            return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
        } else {
            Blog blogFind = optionalBlogFind.get();
            blogFind.setStatus("activo");
            blogRepository.save(blogFind);
            return new ResponseEntity<Blog>(blogFind, HttpStatus.OK);
        }
    }
}
