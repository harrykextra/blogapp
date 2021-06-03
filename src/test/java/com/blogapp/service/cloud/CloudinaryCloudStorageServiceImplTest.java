package com.blogapp.service.cloud;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class CloudinaryCloudStorageServiceImplTest {

    @Autowired @Qualifier("cloudinary")
    CloudStorageService cloudStorageServiceImpl;

    @BeforeEach
    void setUp() {
    }

    @Test
    void uploadImage(){
        File file = new File("C:\\Dev\\blogapp\\blogapp\\src\\main\\resources\\static\\asset\\images\\amazone.png");
        assertThat(file.exists()).isTrue();

        Map<Object, Object> params = new HashMap<>();
        params.put("public_id", "blog_app/myAuthorImage");
        params.put("overwrite", "true");

        try {
            cloudStorageServiceImpl.uploadImage(file, params);
        }
        catch (IOException e){
            log.info("Error occurred --> {}", e.getMessage());
        }
    }

    @Test
    void uploadMultipartImageFile(){
        File file = new File("C:\\Dev\\blogapp\\blogapp\\src\\main\\resources\\static\\asset\\images\\author-image1.jpg");
        assertThat(file.exists()).isTrue();

        Map<Object, Object> params = new HashMap();
        params.put("public_id", "asa_profile");
        params.put("folder", "blogapp");
        params.put("overwrite", "true");

        try {
            Map<?,?> reponse = cloudStorageServiceImpl.uploadImage(file, params);
            log.info("File Uploaded --> {}", reponse);
        }
        catch (IOException exception){
            log.info("Error occurred --> {}", exception.getMessage());
        }
    }
}