package com.ginda.tictactoe.services;

import com.ginda.tictactoe.repository.GameRepository;

public class StatisticServiceImpl {

    private final GameRepository gameRepository;
    public StatisticServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }
}
