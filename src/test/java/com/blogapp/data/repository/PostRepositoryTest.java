package com.blogapp.data.repository;

import com.blogapp.data.models.Author;
import com.blogapp.data.models.Comment;
import com.blogapp.data.models.Post;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Sql(scripts = {"classpath:db/insert.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void savePostToDBTest(){
        Post blogPost = new Post();
        blogPost.setTitle("What is Fintech?");
        blogPost.setContent("blah blah blah");

        log.info("Created a blog post --> {}", blogPost);
        postRepository.save(blogPost);
        assertThat(blogPost.getId()).isNotNull();
    }

    @Test
    void  throwExceptionWhenSavingPostWithDuplicateTitle(){
        Post blogPost = new Post();
        blogPost.setTitle("What is Fintech?");
        blogPost.setContent("blah blah blah");

        log.info("Created a blog post --> {}", blogPost);
        postRepository.save(blogPost);
        assertThat(blogPost.getId()).isNotNull();

        Post blogPost2 = new Post();
        blogPost2.setTitle("What is Fintech?");
        blogPost2.setContent("blah blah blah");

        log.info("Created a blog post --> {}", blogPost2);
        assertThrows(DataIntegrityViolationException.class, ()-> postRepository.save(blogPost2));
    }

    @Test
    void whenPostIsSaved_thenSaveAuthor(){
        Post blogPost = new Post();
        blogPost.setTitle("What is Fintech?");
        blogPost.setContent("blah blah blah");

        log.info("Created a blog post --> {}", blogPost);

        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Wick");
        author.setEmail("john@mail.com");
        author.setPhoneNumber("09099998888");

        //map relationships
        blogPost.setAuthor(author);
        author.addPost(blogPost);

        postRepository.save(blogPost);
        log.info("blog post after saving --> {}", blogPost);

    }

    @Test
    void findAllPostInTheDBTest(){
        List<Post> existingPosts = postRepository.findAll();
        assertThat(existingPosts).isNotNull();
        assertThat(existingPosts).hasSize(4);
    }

    @Test
    @Transactional
    @Rollback(value = false)
    void deletePostTest(){
        Post savePost = postRepository.findById(41).orElse(null);
        assertThat(savePost).isNotNull();
        log.info("fetched from the database --> {}", savePost);

        postRepository.deleteById(41);

        Post deletedPost = postRepository.findById(savePost.getId()).orElse(null);
        assertThat(deletedPost).isNull();
    }

    @Test
    void updatePostTest(){
        Post blogPost = postRepository.findById(42).orElse(null);
        blogPost.setContent("Best of Fela songs");
        blogPost.setTitle("Basket Mouth");
        postRepository.save(blogPost);

        assertThat(blogPost.getTitle()).isEqualTo("Basket Mouth");
    }

    @Test
    @Transactional
    @Rollback(value = false)
    void updatePostAuthorTest(){
        Post blogPost = postRepository.findById(42).orElse(null);
        blogPost.setTitle("What is Fintech?");
        blogPost.setContent("blah blah blah");

        log.info("Created a blog post --> {}", blogPost);

        Author author = new Author();
        author.setFirstName("Joe");
        author.setLastName("Brown");
        author.setPhoneNumber("08088887777");
        author.setEmail("joe@mail.com");
        author.setProfession("fisherman");

        blogPost.setAuthor(author);
        author.addPost(blogPost);

        postRepository.save(blogPost);

        assertThat(blogPost.getAuthor()).isEqualTo(author);


    }

    @Test
    void addCommentToPostTest(){

        //fetch the post from the db
        Post savedPost = postRepository.findById(43).orElse(null);
        assertThat(savedPost).isNotNull();
        assertThat(savedPost.getComments()).hasSize(0);
        //create a comment object
        Comment comment1 = new Comment("Peter bread","Insightful post");
        Comment comment2 = new Comment("Billy Goat","nice post");
        //map the post and comments
        savedPost.addComment(comment1, comment2);
        //save the post
        postRepository.save(savedPost);

        Post commentedPost = postRepository.findById(savedPost.getId()).orElse(null);
        assertThat(commentedPost).isNotNull();
        assertThat(commentedPost.getComments()).hasSize(2);
        log.info("commented post --> {}",commentedPost);
    }

    @Test
    void findAllPostInDescendingOrderTest(){
        List<Post> allPosts = postRepository.findByOrderByDateCreatedDesc();
        assertThat(allPosts).isNotEmpty();
        log.info("All posts --> {}", allPosts);
        assertTrue(allPosts.get(0).getDateCreated().isAfter(allPosts.get(1).getDateCreated()));

        allPosts.forEach(post -> {
            log.info("Post Date {}", post.getDateCreated());
        });
    }
}