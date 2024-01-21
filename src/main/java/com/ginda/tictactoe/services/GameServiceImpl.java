package com.ginda.tictactoe.services;

import com.ginda.tictactoe.exception.BadRequestException;
import com.ginda.tictactoe.exception.ConflictException;
import com.ginda.tictactoe.exception.NotFoundException;
import com.ginda.tictactoe.model.Board;
import com.ginda.tictactoe.model.Score;
import com.ginda.tictactoe.model.Status;
import com.ginda.tictactoe.model.User;
import com.ginda.tictactoe.model.request.CreateGameRequest;
import com.ginda.tictactoe.model.request.Move;
import com.ginda.tictactoe.model.response.CreateGameResponse;
import com.ginda.tictactoe.model.response.Game;
import com.ginda.tictactoe.repository.GameRepository;
import com.ginda.tictactoe.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class GameServiceImpl implements GameService{

    private final GameRepository gameRepository;
    private final UserRepository userRepository;
//    private final Faker faker;

    private int gameId = 1;

    public GameServiceImpl(GameRepository gameRepository, UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CreateGameResponse createNewGame(CreateGameRequest request) throws BadRequestException {
        validateCreateNewGameRequest(request);

        int userId = getUserId(request.getUserName());
        String userName = getUserName(request.getUserName());
        if(!isUserNameAvailable(userName)) { //if user already exist dont save
            User user = new User();
            user.setUserName(userName);
            user.setId(userId);
            userRepository.getUserList().add(user);
        }

        Game game = new Game();
        game.setGridSize(request.getSize());
        game.setUserName(userName);
        game.setScore(createInitialScore());
        game.setBoardList(createNewBoardList(request.getSize()));
        while (!isGameIdAvailable(gameId)) {
            gameId+=1;
        }
        game.setId(gameId);

        gameRepository.getGameList().add(game);

        CreateGameResponse response = new CreateGameResponse();
        response.setUserName(userName);
        response.setBoard("http://localhost:8081/tic-tac-toe/game"+gameId);
        return response;

    }

    @Override
    public String move(Move move, int gameId) throws BadRequestException, NotFoundException, ConflictException {
        validateMoveRequest(move, gameId);
        Board currentBoard = getCurrentBoard(gameId);
        char currentChar;
        if (currentBoard.getFilledGrid()%2==0){
            currentChar = 'X';
        } else {
            currentChar = 'O';
        }
        char [][] currentGrid = currentBoard.getGrid();
        currentGrid[move.getRow()-1][move.getColumn()-1] = currentChar;
        Status boardStatus =


        currentBoard.setGrid(currentGrid);


        return null;
    }

    private void validateCreateNewGameRequest(CreateGameRequest request) throws BadRequestException {
        if (request.getSize()<3) {
            log.error("User wants to create board with size {}", request.getSize());
            throw new BadRequestException("Value can't be smaller than 3", "size");
        }
    }

    private int getUserId(String userName) {
        if (!ObjectUtils.isEmpty(userName)) {
            for (User user: userRepository.getUserList()) {
                if (user.getUserName().equals(userName)) {
                    return user.getId();
                }
            }
        }
        int generatedUserId = 1;
        while (!isUserIdAvailable(generatedUserId)) {
            generatedUserId+=1;
        }
        return generatedUserId;
    }

    private String getUserName(String userName) {
        if (ObjectUtils.isEmpty(userName)) {
            int userNameSuffix = 1;
            String generatedUserName = "user"+userNameSuffix;
            while (!isUserNameAvailable(generatedUserName)) {
                userNameSuffix+=1;
                generatedUserName = "user"+userNameSuffix;
            }
        }
        return userName;
    }

    private boolean isUserIdAvailable(int userId) {
        for (User user:userRepository.getUserList()) {
            if (userId == user.getId()) {
                return false;
            }
        }
        return true;
    }

    private boolean isUserNameAvailable(String userName) {
        for (User user:userRepository.getUserList()) {
            if (userName.equals(user.getUserName())) {
                return false;
            }
        }
        return true;
    }

    private boolean isGameIdAvailable(int gameId) {
        for (Game game: gameRepository.getGameList()) {
            if (gameId == game.getId()) {
                return false;
            }
        }
        return true;
    }

    private List<Board> createNewBoardList(int boardSize) {
        char[][] grid = new char[boardSize][boardSize];
        Board board = new Board();
        board.setId(1);
        board.setGrid(grid);
        return Collections.singletonList(board);
    }

    private Score createInitialScore() {
        Score score = new Score();
        score.setoScore(0);
        score.setxScore(0);
        return score;
    }

    private void validateMoveRequest(Move move, int gameId) throws
            NotFoundException, BadRequestException, ConflictException {
        boolean isGameExist = false;
        Game getGame = new Game();
        for (Game game : gameRepository.getGameList()) {
            if (game.getId()==gameId) {
                isGameExist = true;
                getGame = game;
            }
        }
        if (!isGameExist) {
            log.error("Game with id {} does not exist.", gameId);
            throw new NotFoundException("Game doesn't exist");
        }

        if (
                move.getColumn()<1 ||
                move.getRow()<1 ||
                move.getColumn()>getGame.getGridSize() ||
                move.getRow()>getGame.getGridSize()
        ) {
            log.error("User wants to make move to {}, {}", move.getRow(), move.getColumn());
            String fieldName = move.getColumn()>getGame.getGridSize() || move.getColumn()<1? "column":"row";
            throw new BadRequestException("Not a valid grid position", fieldName);
        }

        int currentBoard = getGame.getBoardList().size()-1;
        Board board = getGame.getBoardList().get(currentBoard);
        char selectedGrid = board.getGrid()[move.getRow()-1][move.getColumn()-1];
        if (selectedGrid == 'X' || selectedGrid == 'O') {
            log.error("grid {}, {} already filled ", move.getRow(), move.getColumn());

            throw new ConflictException(String.format("grid %s, %s already filled ", move.getRow(), move.getColumn()));
        }

    }

    private Board getCurrentBoard(int gameId) {
        for (Game game : gameRepository.getGameList()) {
            if (game.getId()==gameId) {
                return game.getBoardList().get((game.getBoardList().size())-1);
            }
        }
        return null;
    }

    private void setCurrentBoard(Board board, int gameId) {
        for (Game game : gameRepository.getGameList()) {
            if (game.getId()==gameId) {
                game.getBoardList().set((game.getBoardList().size())-1, board);
                break;
            }
        }
    }

    private boolean getBoardStatus(Board currentBoard, int row, int column, char currentChar) {
        if () {

        }

    }

}
