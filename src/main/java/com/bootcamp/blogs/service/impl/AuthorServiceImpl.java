package com.bootcamp.blogs.service.impl;

import com.bootcamp.blogs.entity.Author;
import com.bootcamp.blogs.entity.Blog;
import com.bootcamp.blogs.entity.Comment;
import com.bootcamp.blogs.entity.Post;
import com.bootcamp.blogs.repository.AuthorRepository;
import com.bootcamp.blogs.repository.BlogRepository;
import com.bootcamp.blogs.repository.CommentRepository;
import com.bootcamp.blogs.repository.PostRepository;
import com.bootcamp.blogs.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public ResponseEntity<List<Author>> findAll() {

        List<Author> authorList = authorRepository.findAll();

        if (authorList.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(authorList, HttpStatus.OK);
        }

    }

    @Override
    public ResponseEntity<Author> findById(Long id) {
        Optional<Author> findAuthor =authorRepository.findById(id);

        if (findAuthor.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(findAuthor.get(),HttpStatus.OK);
        }

    }

    @Override
    public ResponseEntity<Author> save(Author author) {

        try {

            if (author.getBlogList() != null) {

                if (author.getBlogList().size() >= 3) {
                    return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
                } else {
                    author.setBlogList(author.getBlogList());
                }

            } else {
                author.setBlogList(new ArrayList<>());
            }

            Author authorSaved = authorRepository.save(author);
            return new ResponseEntity<>(authorSaved, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {

        try {

            Optional<Author> optionalFindAuthor =authorRepository.findById(id);

            if (optionalFindAuthor.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {

                Author findAuthor = optionalFindAuthor.get();

                List<Blog> blogList = findAuthor.getBlogList();

                for (Blog blogItem : blogList) {

                    List<Post> postList = blogItem.getPostList();

                    for (Post postItem : postList) {

                        List<Comment> commentList = postItem.getCommentList();

                            for (Comment commentItem : commentList) {

                                commentRepository.delete(commentItem);

                            }

                        postRepository.delete(postItem);

                    }

                    blogRepository.delete(blogItem);
                }

                authorRepository.delete(findAuthor);
                return new ResponseEntity<>(HttpStatus.OK);
            }

        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<Author> update(Long id, Author author) {
        Optional<Author> optionalFindAuthor =authorRepository.findById(id);

        if (optionalFindAuthor.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Author findAuthor = optionalFindAuthor.get();

            findAuthor.setName(author.getName() != null ? author.getName() : findAuthor.getName());
            findAuthor.setPhone(author.getPhone() != null ? author.getPhone() : findAuthor.getPhone());
            findAuthor.setEmail(author.getEmail() != null ? author.getEmail() : findAuthor.getEmail());
            findAuthor.setBirthDate(author.getBirthDate() != null ? author.getBirthDate() : findAuthor.getBirthDate());
            findAuthor.setBlogList(author.getBlogList() != null ? author.getBlogList() : findAuthor.getBlogList());

            authorRepository.save(findAuthor);

            return new ResponseEntity<>(findAuthor,HttpStatus.OK);
        }
    }
}
