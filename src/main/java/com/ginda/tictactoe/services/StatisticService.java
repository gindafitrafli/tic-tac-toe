package com.ginda.tictactoe.services;

import com.ginda.tictactoe.model.response.Game;

import java.util.List;

public interface StatisticService {

    Game getGameStatisticByGameId(int gameId);
    List<Game> getGameStatisticByUserName(String userName);
}
