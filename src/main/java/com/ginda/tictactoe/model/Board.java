package com.ginda.tictactoe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Board {

    @JsonIgnore
    private int id;
    private char[][] grid;
    @JsonIgnore
    private int filledGrid;
    @JsonIgnore
    private int gridSize;
    private Status status;

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

    public int getFilledGrid() {
        return filledGrid;
    }

    public void setFilledGrid(int filledGrid) {
        this.filledGrid = filledGrid;
    }

    public int getGridSize() {
        return gridSize;
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
