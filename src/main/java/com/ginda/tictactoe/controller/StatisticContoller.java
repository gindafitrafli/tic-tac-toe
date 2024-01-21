package com.ginda.tictactoe.controller;

import com.ginda.tictactoe.model.response.Game;
import com.ginda.tictactoe.services.StatisticService;
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

    private final StatisticService service;

    public StatisticContoller(StatisticService service) {
        this.service = service;
    }

    @GetMapping(value = "/game/{gameId}", produces = "application/json")
    public ResponseEntity<Game> getGameStatisticByGameId(@PathVariable int gameId) {
        log.info("get statistic by id");
        Game response = service.getGameStatisticByGameId(gameId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/user/{userName}", produces = "application/json")
    public ResponseEntity<List<Game>> getGameStatisticByUserName(@PathVariable String userName) {
        log.info("get statistic by userid");
        List<Game> response = service.getGameStatisticByUsestatixticrName(userName);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
