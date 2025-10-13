package com.web.controllers;

import com.web.dto.response.ApiResponse;
import com.web.model.Card;
import com.web.repository.CardRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CardController {
    CardRepository cardRepository;

    @PostMapping
    ApiResponse<Card> create(@RequestBody Card card) {
        return ApiResponse.<Card>builder().result(cardRepository.save(card)).build();
    }

    @GetMapping
    ApiResponse<List<Card>> list() {
        return ApiResponse.<List<Card>>builder().result(cardRepository.findAll()).build();
    }
}


