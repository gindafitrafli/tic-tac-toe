# tic-tac-toe
This is a simple tic-tac-toe game based on rest api. 
Prerequisites:
* Java-17

## How to run
* Clone this repo by running
```
git@github.com:gindafitrafli/tic-tac-toe.git
```
* And open the project then run 
```
./gradlew bootRun
```

## How to use
* First we need to create a game. You can create set of game by send following request:
```
HTTP 1.1 POST http://localhost:8081/tic-tac-toe/game

{
    "size": 3
}
```
and you will receive a response
```
{
    "board": "http://localhost:8081/tic-tac-toe/game/1",
    "userName": "user1"
}
```
This means we can now play tic-tac-toe with size 3 * 3 on http://localhost:8081/tic-tac-toe/game/1
* To play the game, we can sent request to url that we just obtained.
```
HTTP 1.1 POST http://localhost:8081/tic-tac-toe/game/1

{
    "row": 1,
    "column": 1
}
```
this request will fill up the board with X and O. The first move is always X's turn, the second one is O's turn and so 
on and so forth (for now). 
```
{
    "grid": [
        "OO\u0000",
        "XXX",
        "\u0000\u0000\u0000"
    ],
    "status":"X_WIN",
    "score": {
        "xScore":1,
        "oScore":0
    }
}
```
Once the winner decided, the score will be updated, but don't worry. If you want to 
continue the game just fill the grid, the board will update itself.
* Once you finish the game you can see your game history on
```
HTTP 1.1 GET http://localhost:8081/statistic/game/1
```
or you can get your playing history by requesting 
```
HTTP 1.1 GET http://localhost:8081//user/user1
```



