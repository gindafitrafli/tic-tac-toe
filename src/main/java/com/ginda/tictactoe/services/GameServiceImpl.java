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
import com.ginda.tictactoe.model.response.BoardResponse;
import com.ginda.tictactoe.model.response.CreateGameResponse;
import com.ginda.tictactoe.model.response.Game;
import com.ginda.tictactoe.repository.GameRepository;
import com.ginda.tictactoe.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;

import static java.lang.Math.pow;

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
        if(isUserNameAvailable(userName)) { //if user already exist dont save
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
        response.setBoard("http://localhost:8081/tic-tac-toe/game/"+gameId);
        return response;
    }

    @Override
    public BoardResponse move(Move move, int gameId) throws BadRequestException, NotFoundException, ConflictException {
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
        currentBoard.setGrid(currentGrid);
        currentBoard.setFilledGrid(currentBoard.getFilledGrid()+1);
        Status currentBoardStatus = getBoardStatus(currentBoard, move.getRow() - 1, move.getColumn() - 1, currentChar);

        currentBoard.setStatus(currentBoardStatus);
        setCurrentBoard(currentBoard, gameId);
        BoardResponse boardResponse = new BoardResponse();
        boardResponse.setStatus(currentBoardStatus);
        boardResponse.setScore(updateScore(gameId, currentBoardStatus));
        boardResponse.setGrid(currentGrid);
        return boardResponse;
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
            return generatedUserName;
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
        return Collections.singletonList(createNewBoard(boardSize, 1));
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

        Board board = getCurrentBoard(gameId);
        char selectedGrid = board.getGrid()[move.getRow()-1][move.getColumn()-1];
        if (selectedGrid == 'X' || selectedGrid == 'O') {
            log.error("grid {}, {} already filled ", move.getRow(), move.getColumn());

            throw new ConflictException(String.format("grid %s, %s already filled ", move.getRow(), move.getColumn()));
        }

    }

    private Game getCurrentGame(int gameId) {
        return gameRepository.getGameList().stream().filter(game -> game.getId()==gameId).findFirst().orElse(null);
    }

    private Board getCurrentBoard(int gameId) {
        Game currentGame = getCurrentGame(gameId);
        List<Board> boardList = currentGame.getBoardList();

        Board currentBoard = boardList.stream().filter(board -> board.getStatus() == Status.ON_GOING)
                .findFirst().orElse(null);
        if (currentBoard==null){
            currentBoard = createNewBoard(currentGame.getGridSize(), boardList.size()+1);
        }
        return currentBoard;

    }

    private Board createNewBoard(int gridSize, int id) {
        char[][] grid = new char[gridSize][gridSize];
        Board board = new Board();
        board.setId(id);
        board.setGrid(grid);
        board.setGridSize(gridSize);
        board.setStatus(Status.ON_GOING);
        board.setFilledGrid(0);
        return board;
    }

    private void setCurrentBoard(Board board, int gameId) {
        boolean isBoardNew = true;
        for (Game game : gameRepository.getGameList()) {
            if (game.getId()==gameId) {
                for (Board board1: game.getBoardList()){
                    if (board1.getId() == board.getId()){
                        board1 = board;
                        isBoardNew = false;
                        break;
                    }
                }
                if (isBoardNew) {
                    gameRepository.addNewBoardByGameId(board, gameId);
                }
            }
        }
    }

    private Status getBoardStatus(Board currentBoard, int row, int column, char currentChar) {
        boolean rowCheck=true;
        boolean columnCheck=true;
        boolean diag1Check=true;
        boolean diag2Check=true;
        char[] [] currentGrid = currentBoard.getGrid();
        for (int i =0; i< currentBoard.getGridSize(); i++) {
            rowCheck = rowCheck && (currentGrid[row][i]==currentChar);
        }
        for (int i =0; i< currentBoard.getGridSize(); i++) {
            columnCheck = columnCheck && (currentGrid[i][column]==currentChar);
        }
        for (int i =0; i< currentBoard.getGridSize(); i++) {
            diag1Check = diag1Check && (currentGrid[i][i]==currentChar);
        }
        for (int i =0; i< currentBoard.getGridSize(); i++) {
            diag2Check = diag2Check && (currentGrid[currentBoard.getGridSize()-i-1][i]==currentChar);
        }

        if (diag2Check || diag1Check || columnCheck || rowCheck) {
            if (currentChar=='X') {
                return Status.X_WIN;
            }
            return Status.O_WIN;
        }

        if (currentBoard.getFilledGrid()==(pow(currentBoard.getGridSize(), 2))) {
            return Status.DRAW;
        }

        return Status.ON_GOING;
    }

    private Score updateScore(int gameId, Status status) {
        if (status == Status.ON_GOING || status == Status.DRAW) {
            return gameRepository.getGameList().stream().filter(game -> game.getId()==gameId).findFirst().orElse(null).getScore();
        }
        int currentGameIdx = 0;
        for (Game game:gameRepository.getGameList()) {
            if (game.getId()==gameId) {
                break;
            }
            currentGameIdx+=1;
        }
        Game currentGame = gameRepository.getGameList().get(currentGameIdx);
        Score currentScore = currentGame.getScore();
        currentGame.setScore(currentScore);
        if (status==Status.O_WIN){
            currentScore.setoScore(currentScore.getoScore()+1);
        } else if(status==Status.X_WIN) {
            currentScore.setxScore(currentScore.getxScore()+1);
        }
        currentGame.setScore(currentScore);
        gameRepository.getGameList().set(currentGameIdx,currentGame);

        return currentScore;
    }

}
