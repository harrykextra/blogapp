package com.blogapp.web.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
@Data
public class PostDto {

    @NotEmpty(message = "Title can npt be null")
    private String title;

    @NotEmpty(message = "Content should not be null")
    private String content;

    private MultipartFile imageFile;
}
