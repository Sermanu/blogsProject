package com.bootcamp.blogs.service.impl;

import com.bootcamp.blogs.entity.Author;
import com.bootcamp.blogs.entity.Blog;
import com.bootcamp.blogs.entity.Comment;
import com.bootcamp.blogs.entity.Post;
import com.bootcamp.blogs.repository.AuthorRepository;
import com.bootcamp.blogs.repository.BlogRepository;
import com.bootcamp.blogs.repository.CommentRepository;
import com.bootcamp.blogs.repository.PostRepository;
import com.bootcamp.blogs.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public ResponseEntity<List<Blog>> findAll() {
        List<Blog> blogList = blogRepository.findAll();

        if (blogList.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(blogList, HttpStatus.OK);
        }

    }

    @Override
    public ResponseEntity<Blog> findById(Long id) {
        Optional<Blog> blogFind = blogRepository.findById(id);

        if (blogFind.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(blogFind.get(), HttpStatus.OK);
        }

    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        try {

            Optional<Blog> optionalBlog = blogRepository.findById(id);

            if (optionalBlog.isEmpty()) {

                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            } else {

                Blog blogFind = optionalBlog.get();

                List<Post> postsOfBlog = blogFind.getPostList();

                for (Post postItem : postsOfBlog) {

                    List<Comment> commentsOfPost = postItem.getCommentList();

                    for (Comment commentItem : commentsOfPost) {

                        commentRepository.delete(commentItem);

                    }

                    postRepository.delete(postItem);
                }

                blogRepository.delete(blogFind);
                return new ResponseEntity<>(HttpStatus.OK);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Blog> save(Blog blog) {
        try {


            Optional<Author> optionalBlogAuthor = authorRepository.findById(blog.getAuthor().getId());

            if (optionalBlogAuthor.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {

                Author blogAuthor = optionalBlogAuthor.get();

                if (blogAuthor.getBlogList().size() >= 3) {
                    return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
                }

                Date actualDate = new Date();
                Date birthDateAuthor = blogAuthor.getBirthDate();

                LocalDate actualLocalDate = actualDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate birthDateAuthorLocalDate = birthDateAuthor.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                long diff = ChronoUnit.YEARS.between(birthDateAuthorLocalDate, actualLocalDate);

                if (diff < 18) {
                    return new ResponseEntity<>(HttpStatus.PRECONDITION_REQUIRED);
                }

                blog.setStatus("inactivo");
                Blog blogSaved = blogRepository.save(blog);
                return new ResponseEntity<>(blogSaved, HttpStatus.OK);

            }

        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Blog> update(Long id, Blog blog) {
        Optional<Blog> optionalBlogFind = blogRepository.findById(id);

        if (optionalBlogFind.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Blog blogFind = optionalBlogFind.get();

            blogFind.setName(blog.getName() != null ? blog.getName() : blogFind.getName());
            blogFind.setDescription(blog.getDescription() != null ? blog.getDescription() : blogFind.getDescription());
            blogFind.setUrl(blog.getUrl() != null ? blog.getUrl() : blogFind.getUrl());
            blogFind.setAuthor(blog.getAuthor() != null ? blog.getAuthor() : blogFind.getAuthor());
            blogFind.setPostList(blog.getPostList() != null ? blog.getPostList() : blogFind.getPostList());
            blogFind.setStatus(blog.getStatus() != null ? blog.getStatus() : blogFind.getStatus());

            blogRepository.save(blogFind);
            return new ResponseEntity<>(blogFind, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Blog> activateBlog(Long id) {
        Optional<Blog> optionalBlogFind = blogRepository.findById(id);

        if (optionalBlogFind.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Blog blogFind = optionalBlogFind.get();
            blogFind.setStatus("activo");
            blogRepository.save(blogFind);
            return new ResponseEntity<>(blogFind, HttpStatus.OK);
        }
    }
}
