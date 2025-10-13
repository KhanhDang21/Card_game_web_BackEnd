package com.web.repository;

import com.web.model.GameBoard;
import com.web.model.User;
import com.web.model.UserGameBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserGameBoardRepository extends JpaRepository<UserGameBoard, Integer> {
    List<UserGameBoard> findByGameBoard(GameBoard gameBoard);

    Optional<UserGameBoard> findByGameBoardAndUser(GameBoard gameBoard, User user);
}


