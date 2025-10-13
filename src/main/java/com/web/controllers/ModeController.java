package com.web.controllers;

import com.web.dto.request.ModeCreateRequest;
import com.web.dto.response.ApiResponse;
import com.web.model.Mode;
import com.web.repository.ModeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/modes")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ModeController {
    ModeRepository modeRepository;

    @PostMapping
    ApiResponse<Mode> create(@RequestBody ModeCreateRequest request) {
        Mode mode = Mode.builder().name(request.getName()).build();

        return ApiResponse.<Mode>builder()
                .result(modeRepository.save(mode))
                .build();
    }

    @GetMapping
    ApiResponse<List<Mode>> list() {
        return ApiResponse.<List<Mode>>builder()
                .result(modeRepository.findAll())
                .build();
    }
}


