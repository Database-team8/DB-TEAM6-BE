package com.ajoufinder.be.board.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Board", description = "게시글 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
public class BoardController {
}
