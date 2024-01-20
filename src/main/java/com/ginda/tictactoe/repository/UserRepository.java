package com.ginda.tictactoe.repository;

import com.ginda.tictactoe.model.User;
import com.ginda.tictactoe.model.response.Game;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private final List<User> userList;
    public UserRepository() {
        this.userList = new ArrayList<>();
    }

    public List<User> getUserList() {
        return this.userList;
    }
}
