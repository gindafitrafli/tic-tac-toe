package com.ginda.tictactoe.model.response;

import com.ginda.tictactoe.model.Board;
import com.ginda.tictactoe.model.Score;
import lombok.Builder;

import java.util.List;

public class Game {

    private int Id;
    private int gridSize;
    private Score score;
    private List<Board> boardList;
    private String userName; //important for statistic

    //todo: implement following feature
    private boolean isTimed;
    private int time; //so it can be played against time, player that run out of time will automatically lose
    private boolean isVsBot;
    private boolean playedX; //needed if play against bot

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getGridSize() {
        return gridSize;
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public List<Board> getBoardList() {
        return boardList;
    }

    public void setBoardList(List<Board> boardList) {
        this.boardList = boardList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isTimed() {
        return isTimed;
    }

    public void setTimed(boolean timed) {
        isTimed = timed;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public boolean isVsBot() {
        return isVsBot;
    }

    public void setVsBot(boolean vsBot) {
        isVsBot = vsBot;
    }

    public boolean isPlayedX() {
        return playedX;
    }

    public void setPlayedX(boolean playedX) {
        this.playedX = playedX;
    }
}
