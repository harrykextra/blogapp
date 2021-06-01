package com.blogapp.data.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "comment")
@Data
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue
    private UUID id;
    @Column(nullable = false, length = 150)
    private String content;

    private String commenter;

    @CreationTimestamp
    private LocalDate dateCreated;

    public Comment(String commenter, String content){
        this.content = content;
        this.commenter = commenter;
    }
}
