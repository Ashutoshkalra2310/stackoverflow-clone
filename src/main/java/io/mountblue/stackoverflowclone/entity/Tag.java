package io.mountblue.stackoverflowclone.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name="tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String name;
    String description;
    @ManyToMany(mappedBy = "tags")
    private Set<Question> questions;

    public Tag(String trim) {
    }
}
