package com.ginda.tictactoe.model.response;

import com.ginda.tictactoe.model.Board;
import com.ginda.tictactoe.model.Score;
import com.ginda.tictactoe.model.Status;

public class BoardResponse extends Board {
    private Status status;
    private Score score;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }
}
