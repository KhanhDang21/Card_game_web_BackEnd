package com.web.services;

import com.web.exception.AppException;
import com.web.exception.ErrorCode;
import com.web.model.*;
import com.web.repository.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GameService {
    GameBoardRepository gameBoardRepository;
    UserGameBoardRepository userGameBoardRepository;
    ModeRepository modeRepository;
    CardSetRepository cardSetRepository;
    UserRepository userRepository;

    @Transactional
    public GameBoard createGame(Integer modeId, Integer userAId, Integer userBId) {
        Mode mode = modeRepository.findById(modeId)
                .orElseThrow(() -> new AppException(ErrorCode.MODE_NOT_FOUND));

        // For simplicity, pick any existing CardSet for this mode.
        CardSet cardSet = cardSetRepository.findAll().stream()
                .filter(cs -> cs.getMode().getId() == mode.getId())
                .findFirst()
                .orElseThrow(() -> new AppException(ErrorCode.CARD_SET_NOT_FOUND));

        GameBoard gameBoard = GameBoard.builder()
                .cardSet(cardSet)
                .startTime(OffsetDateTime.now())
                .build();

        gameBoard = gameBoardRepository.save(gameBoard);

        User userA = userRepository.findById(userAId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        User userB = userRepository.findById(userBId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        userGameBoardRepository.save(UserGameBoard.builder().gameBoard(gameBoard).user(userA).score(0).build());
        userGameBoardRepository.save(UserGameBoard.builder().gameBoard(gameBoard).user(userB).score(0).build());

        return gameBoard;
    }

    public List<UserGameBoard> getScores(Integer gameBoardId) {
        GameBoard gameBoard = gameBoardRepository.findById(gameBoardId)
                .orElseThrow(() -> new AppException(ErrorCode.CARD_SET_NOT_FOUND));

        return userGameBoardRepository.findByGameBoard(gameBoard);
    }

    @Transactional
    public void updateScore(Integer gameBoardId, Integer userId, int delta) {
        GameBoard gameBoard = gameBoardRepository.findById(gameBoardId)
                .orElseThrow(() -> new AppException(ErrorCode.CARD_SET_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        UserGameBoard ugb = userGameBoardRepository.findByGameBoardAndUser(gameBoard, user)
                .orElseThrow(() -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION));
        ugb.setScore(ugb.getScore() + delta);
        userGameBoardRepository.save(ugb);
    }

    @Transactional
    public GameBoard endGame(Integer gameBoardId) {
        GameBoard gameBoard = gameBoardRepository.findById(gameBoardId)
                .orElseThrow(() -> new AppException(ErrorCode.CARD_SET_NOT_FOUND));
        gameBoard.setEndTime(OffsetDateTime.now());

        // accumulate each player's score into their total_score
        List<UserGameBoard> participants = userGameBoardRepository.findByGameBoard(gameBoard);
        for (UserGameBoard participant : participants) {
            User player = participant.getUser();
            int current = player.getTotal_score();
            player.setTotal_score(current + participant.getScore());
            userRepository.save(player);
        }

        return gameBoardRepository.save(gameBoard);
    }
}


