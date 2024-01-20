package com.ginda.tictactoe.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Game {

    private int Id;
    private int gridSize;
    private Score score;
    private List<Board> boardList;
    private String userId; //important for statistic

    //todo: implement following feature
    private boolean isTimed;
    private int time; //so it can be played against time, player that run out of time will automatically lose
    private boolean isVsBot;
    private boolean playedX; //needed if play against bot



}
