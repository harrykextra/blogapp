package com.blogapp.web.controllers;

import com.blogapp.data.models.Post;
import com.blogapp.service.post.PostService;
import com.blogapp.web.dto.PostDto;
import com.blogapp.web.exceptions.PostDoesNotExistException;
import com.blogapp.web.exceptions.PostObjectIsNullException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/posts")
public class PostController {
    @Autowired
    PostService postServiceImpl;

    @PostMapping("/save")
    public String savePost(@ModelAttribute @Valid PostDto postDto, BindingResult result, Model model){
        log.info("Post dto recieved --> {}", postDto);

        if (result.hasErrors()){
            return "create";
        }

        try {
            postServiceImpl.savePost(postDto);
        }
        catch (PostObjectIsNullException exception){
            log.info("Exception occurred --> {}", exception.getMessage());
        }
        catch (DataIntegrityViolationException dx){
            log.info("Constraint exception occurred --> {}", dx.getMessage());
            model.addAttribute("error", true);
            model.addAttribute("error",dx.getMessage());
            //model.addAttribute("postDto", new PostDto());

            return "create";
        }
        return "redirect:/posts";
    }

    @GetMapping("")
    public String getIndex(Model model){

        List<Post> postList = postServiceImpl.findAllPosts();
        model.addAttribute("postList", postList);
        return "index";
    }

    @GetMapping("/create")
    public String getPostForm(Model model){
        //model.addAttribute("post", new PostDto());
        model.addAttribute("error", false);
        return "create";
    }

    @ModelAttribute
    public void createPostModel(Model model){
        model.addAttribute("postDto", new PostDto());
    }

    @GetMapping("/info/{postId}")
    public String getPostDetails(@PathVariable("postId") Integer postId, Model model){
        log.info("Request for a post path --> {}", postId);

        try{
            Post savedPost = postServiceImpl.findById(postId);
            model.addAttribute("postInfo", savedPost);
            log.info("Post object --> {}", savedPost);
        }catch (PostDoesNotExistException e){
            return "redirect/posts";
        }

        return "post";
    }

    @GetMapping("/view")
    public String viewPost(@RequestParam(name = "postId") Integer postId, Model model){
        log.info("Post Id --> {}", postId);

        try{
            Post post = postServiceImpl.findById(postId);
            model.addAttribute("post", post);
            log.info("Post Object --> {}", post);
        }catch (PostDoesNotExistException e){
            return "redirect:/posts";
        }

        return "post";
    }

}
