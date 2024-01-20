package com.ginda.tictactoe.controller;

import com.ginda.tictactoe.model.Game;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/statistic")
@RestController
public class StatisticContoller {

    @GetMapping("/game/{gameId}")
    public ResponseEntity<Game> getGameStatisticByGameId(@PathVariable String id) {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/user/{userName}")
    public ResponseEntity<List<Game>> getGameStatisticByUserId(@PathVariable String id) {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
