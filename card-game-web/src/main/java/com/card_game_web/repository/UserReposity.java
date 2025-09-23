package com.card_game_web.repository;

import com.card_game_web.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserReposity extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
