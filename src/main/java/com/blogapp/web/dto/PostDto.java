package com.blogapp.web.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
@Data
public class PostDto {

    @NotNull(message = "Title can npt be null")
    private String title;

    @NotNull(message = "Content should not be null")
    private String content;

    private MultipartFile imageFile;
}
