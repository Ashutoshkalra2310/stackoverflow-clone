package io.mountblue.stackoverflowclone.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Question> questions;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Answer> answers;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Vote> votes;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Comment> comments;
}
