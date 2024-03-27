package io.mountblue.stackoverflowclone.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String content;
    private LocalDateTime publishedAt;
    private LocalDateTime updatedAt;


}
