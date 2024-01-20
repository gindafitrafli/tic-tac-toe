package com.ginda.tictactoe.model.response;

public class CreateGameResponse {

    private String board;
    private String userName;

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
