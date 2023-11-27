# -TicTacToe over TCP ❌ ⭕️ - Komarov Sergey & Jano Ahmad

## Welcome to our repository for our practical work #2 of the DAI course.

## Overview :
In this project, we decided to implement a classical game of TicTacToe, playable by 2 players.
Each player is represented by an individual client; and those clients communicate with a server hosting the game.

### Quick rule recap:
- A player is represented by either an X or an O
- One after the other, the players must fill in a 3x3 grid with their simbol, one symbol at a time
- The first player to reach a full line, vertically, horizontally or in one of the 2 diagonals, wins.


## Getting started:
To get playing, you will need:
- Git
- A Java IDE with maven
- A friend... (unfortunately, our AI department is too busy working on other great projects, so for now you can't play against the computer :( )

First of all, you need to clone this repo.
Then, you can navigate to the tcpApp directory, and compile the project using:

```bash
mvn clean package
```

This will generate a .jar file named protocol.jar


Now, you can launch the applications.
- First, launch the server with the following command:
```bash
java -jar target/protocol.jar <-p> [port] <-t> [threads] server
```
Where -p is the port to use, default is 1234, and -t is the number of threads. Here the default is 2 as we have 2 players. Any other value makes no sense here.

- Next, launch the 2 clients separately, with the following command:
```bash
java -jar target/protocol.jar <-p> [port] <-t> [threads] client
```

Once both clients are connected to the server, each player will be greeted with a welcome message, explaining the available commands and their respective player number.

For the sake of redundancy, we provide them here as well:
- PLAY X Y :  Play a move on the grid at coordinates (X,Y). Note that (0,0) is in the top left corner.
- RESET : Reset the game to the initial state
- QUIT : Quit the game and disconnect from the server
- HELP : display the help message

With those commands in mind, you can start playing!


