package com.web.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "game_board")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GameBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "card_set_id")
    CardSet cardSet;

    @Column(name = "start_time")
    OffsetDateTime startTime;

    @Column(name = "end_time")
    OffsetDateTime endTime;
}


