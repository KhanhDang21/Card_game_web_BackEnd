package com.web.controllers;

import com.web.dto.response.ApiResponse;
import com.web.dto.response.GameCreateResponse;
import com.web.model.UserGameBoard;
import com.web.services.GameService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GameController {
    GameService gameService;

    @PostMapping("/create")
    ApiResponse<GameCreateResponse> create(@RequestParam Integer modeId,
                                           @RequestParam Integer userAId,
                                           @RequestParam Integer userBId) {
        var game = gameService.createGame(modeId, userAId, userBId);
        return ApiResponse.<GameCreateResponse>builder()
                .result(GameCreateResponse.builder().gameBoardId(game.getId()).build())
                .build();
    }

    @GetMapping("/{gameBoardId}/scores")
    ApiResponse<List<UserGameBoard>> scores(@PathVariable Integer gameBoardId) {
        return ApiResponse.<List<UserGameBoard>>builder()
                .result(gameService.getScores(gameBoardId))
                .build();
    }

    @PostMapping("/{gameBoardId}/score")
    ApiResponse<Void> addScore(@PathVariable Integer gameBoardId,
                               @RequestParam Integer userId,
                               @RequestParam int delta) {
        gameService.updateScore(gameBoardId, userId, delta);
        return ApiResponse.<Void>builder().build();
    }

    @PostMapping("/{gameBoardId}/end")
    ApiResponse<Void> end(@PathVariable Integer gameBoardId) {
        gameService.endGame(gameBoardId);
        return ApiResponse.<Void>builder().build();
    }
}


