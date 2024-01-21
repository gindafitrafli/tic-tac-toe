package com.ginda.tictactoe.services;

import com.ginda.tictactoe.model.response.Game;
import com.ginda.tictactoe.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticServiceImpl implements StatisticService{

    private final GameRepository gameRepository;
    public StatisticServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public Game getGameStatisticByGameId(int gameId) {
        Game game = gameRepository.getGameList().stream().filter(game1 -> game1.getId()==gameId).findFirst().orElse(new Game());
        return game;
    }

    @Override
    public List<Game> getGameStatisticByUserName(String userName) {
        return gameRepository.getGameList().stream().filter(game -> game.getUserName().equals(userName)).toList();
    }
}
