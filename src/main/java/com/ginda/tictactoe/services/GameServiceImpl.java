package com.ginda.tictactoe.services;

import com.ginda.tictactoe.exception.BadRequestException;
import com.ginda.tictactoe.model.request.CreateGameRequest;
import com.ginda.tictactoe.model.request.Move;
import com.ginda.tictactoe.model.response.CreateGameResponse;
import com.ginda.tictactoe.model.response.Game;
import com.ginda.tictactoe.repository.GameRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@Slf4j
public class GameServiceImpl implements GameService{

    private final GameRepository gameRepository;
    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public CreateGameResponse createNewGame(CreateGameRequest request) throws BadRequestException{
        validateCreateNewGameRequest(request);
        boolean isUserNameAvailable = isUserNameAvaiable(request);
        if (gameRepository.getGameList().isEmpty() || ObjectUtils.isEmpty(request.getUserName()) || isUserNameAvailable)

        return ;
    }

    @Override
    public String move(Move move, int gameId) {
        return null;
    }

    private void validateCreateNewGameRequest(CreateGameRequest request) throws BadRequestException {
        if (request.getSize()<3) {
            log.error(String.format("User wants to create board with size %s", request.getSize()));
            throw new BadRequestException("Value can't be smaller than 3", "size");
        }
    }

    private boolean isUserNameAvaiable(CreateGameRequest request) {
        if (gameRepository.getGameList().isEmpty() || ObjectUtils.isEmpty(request.getUserName())) {
            for(Game game: gameRepository.getGameList()) {
                if (request.getUserName().equals(game.getUserName())) {
                    return true;
                }
            }
        }
        return false;
    }


}