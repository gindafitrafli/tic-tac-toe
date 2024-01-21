package com.ginda.tictactoe.model.response;

import com.ginda.tictactoe.model.Board;
import com.ginda.tictactoe.model.Score;
import com.ginda.tictactoe.model.Status;

public class BoardResponse extends Board {

    private Score score;

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }
}
