package com.ginda.tictactoe.repository;

import com.ginda.tictactoe.model.response.Game;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GameRepository {

    private final List<Game> gameList;
    public GameRepository() {
        this.gameList = new ArrayList<>();
    }

    public List<Game> getGameList() {
        return this.gameList;
    }
}
