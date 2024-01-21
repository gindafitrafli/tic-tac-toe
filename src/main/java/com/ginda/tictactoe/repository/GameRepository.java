package com.ginda.tictactoe.repository;

import com.ginda.tictactoe.model.Board;
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

    public void addNewBoardByGameId(Board board, int gameId) {
        Game currentGame;
        int currentIdx=0;
        for (Game game: gameList) {
            if (game.getId()==gameId){
                currentGame = game;
            }
            currentIdx+=1;
        }

        List<Board> boardList = currentGame.getBoardList();
        boardList.add(board);
        currentGame.setBoardList(boardList);
        gameList.set(currentIdx, currentGame);
    }
}
