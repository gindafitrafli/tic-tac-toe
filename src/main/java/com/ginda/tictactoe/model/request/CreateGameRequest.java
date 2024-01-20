package com.ginda.tictactoe.model.request;

import lombok.Data;


public class CreateGameRequest {

    private int size;
    private String userName;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
