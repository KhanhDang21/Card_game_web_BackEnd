package com.web.controllers;

import com.web.dto.response.ApiResponse;
import com.web.model.Card;
import com.web.model.CardSet;
import com.web.model.Mode;
import com.web.repository.CardRepository;
import com.web.repository.CardSetRepository;
import com.web.repository.ModeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/card-sets")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CardSetController {
    CardSetRepository cardSetRepository;
    ModeRepository modeRepository;
    CardRepository cardRepository;

    @PostMapping
    ApiResponse<CardSet> create(@RequestParam Integer modeId, @RequestParam Integer cardId) {
        Mode mode = modeRepository.findById(modeId).orElseThrow();
        Card card = cardRepository.findById(cardId).orElseThrow();
        CardSet saved = cardSetRepository.save(CardSet.builder().mode(mode).card(card).build());
        return ApiResponse.<CardSet>builder().result(saved).build();
    }

    @GetMapping
    ApiResponse<List<CardSet>> list() {
        return ApiResponse.<List<CardSet>>builder().result(cardSetRepository.findAll()).build();
    }
}


