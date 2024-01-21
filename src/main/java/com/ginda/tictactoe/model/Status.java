package com.ginda.tictactoe.model;

public enum Status {
    X_WIN("X win"),

    O_WIN("O win"),

    DRAW("Nobody wins"),
    ON_GOING("On going");
    private final String value;
     Status(String value) {
        this.value = value;
    }


    public String value() {
        return this.value;
    }

}
