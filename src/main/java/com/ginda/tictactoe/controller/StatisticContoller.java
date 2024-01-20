package com.ginda.tictactoe.controller;

import com.ginda.tictactoe.model.response.Game;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("/statistic")
@RestController
public class StatisticContoller {

    @GetMapping(value = "/game/{gameId}", produces = "application/json")
    public ResponseEntity<Game> getGameStatisticByGameId(@PathVariable String gameId) {
        log.info("get statistic by id");
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping(value = "/user/{userName}", produces = "application/json")
    public ResponseEntity<List<Game>> getGameStatisticByUserId(@PathVariable String userName) {
        log.info("get statistic by userid");
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
