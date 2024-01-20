package com.ginda.tictactoe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Board {

    @JsonIgnore
    private int id;
    private char[][] grid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public char[][] getGrid() {
        return grid;
    }

    public void setGrid(char[][] grid) {
        this.grid = grid;
    }
}
