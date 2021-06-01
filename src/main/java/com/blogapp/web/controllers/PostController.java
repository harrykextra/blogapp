package com.blogapp.web.controllers;

import com.blogapp.service.post.PostService;
import com.blogapp.web.dto.PostDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@Slf4j
@RequestMapping("/posts")
public class PostController {
    @Autowired
    PostService postServiceImpl;

    @PostMapping("/save")
    public String savePost(@RequestBody @Valid PostDto postDto){
        log.info("Post dto recieved --> {}", postDto);
        return "index";
    }

    @GetMapping("/")
    public String getIndex(){
        return "index";
    }

    @GetMapping("/create")
    public String getPostForm(Model model){
        model.addAttribute("post", new PostDto());
        return "create";
    }
}
