package com.web.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@Table(name = "user_game_board")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserGameBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "game_board_id")
    GameBoard gameBoard;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    User user;

    @Column
    int score;
}


