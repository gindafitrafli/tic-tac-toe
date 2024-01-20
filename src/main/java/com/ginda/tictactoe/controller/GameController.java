package com.ginda.tictactoe.controller;

import com.ginda.tictactoe.model.request.CreateGame;
import com.ginda.tictactoe.model.request.Move;
import com.ginda.tictactoe.model.response.BoardResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;


@RequestMapping("/tic-tac-toe")
@ResponseBody
public class GameController {

    private static final String REDIRECT_TO_GAME = "http://localhost/tic-tac-toe/game/%s";
    @PostMapping(value = "/game")
    public ResponseEntity<Void> game(@RequestBody CreateGame game) throws URISyntaxException {
        String id = "";
        return ResponseEntity.created(new URI(String.format(REDIRECT_TO_GAME).formatted(id))).build();
    }

    @PutMapping(value = "/game/{gameId}", produces = "text/plain")
    public ResponseEntity<String> move(@RequestBody Move move) {
        return ResponseEntity.ok().build();
    }

}
