package com.web.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@Table(name = "user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;


    @Column(unique = true, nullable = false)
    String username;

    @Column(nullable = false)
    @JsonIgnore
    String password;

    @Column(nullable = true)
    int status;

    @Column(nullable = true)
    int total_score;


    @Column(nullable = true)
    int total_quit_game;


}
