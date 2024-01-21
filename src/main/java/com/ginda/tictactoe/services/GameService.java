package com.ginda.tictactoe.services;

import com.ginda.tictactoe.exception.BadRequestException;
import com.ginda.tictactoe.exception.ConflictException;
import com.ginda.tictactoe.exception.NotFoundException;
import com.ginda.tictactoe.model.request.CreateGameRequest;
import com.ginda.tictactoe.model.request.Move;
import com.ginda.tictactoe.model.response.CreateGameResponse;

public interface GameService {

    CreateGameResponse createNewGame(CreateGameRequest request) throws BadRequestException;

    String move(Move move, int gameId) throws BadRequestException, NotFoundException, ConflictException;

}
