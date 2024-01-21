package com.ginda.tictactoe.controller;

import com.ginda.tictactoe.exception.BadRequestException;
import com.ginda.tictactoe.exception.ConflictException;
import com.ginda.tictactoe.exception.NotFoundException;
import com.ginda.tictactoe.model.request.CreateGameRequest;
import com.ginda.tictactoe.model.request.Move;
import com.ginda.tictactoe.model.response.BoardResponse;
import com.ginda.tictactoe.model.response.CreateGameResponse;
import com.ginda.tictactoe.services.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/tic-tac-toe")
@RestController
public class GameController {

    private final GameService service;

    public GameController(GameService gameService) {
        this.service = gameService;
    }

    private static final String REDIRECT_TO_GAME = "http://localhost:8081/tic-tac-toe/game/%s";

    @GetMapping(value = "/game", produces = "application/json", consumes = "application/json")
    public ResponseEntity<CreateGameResponse> createNewGame(@RequestBody CreateGameRequest request) throws BadRequestException {
        log.info("create new game");
        CreateGameResponse response = service.createNewGame(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping(value = "/game/{gameId}", produces = "text/plain", consumes = "application/json")
    public ResponseEntity<BoardResponse> move(@RequestBody Move move, @PathVariable int gameId) throws ConflictException, BadRequestException, NotFoundException {
        log.info("make move");
        BoardResponse response = service.move(move, gameId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
