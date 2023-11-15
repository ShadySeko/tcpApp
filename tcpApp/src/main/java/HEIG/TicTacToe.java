package HEIG;

/**
 * This class modelizes a TicTacToe game.
 */
public class TicTacToe {

    private int[][] board;
    private int currentPlayer;
    private int winner;
    private int turn;
    private boolean gameEnded;

    public TicTacToe() {
        board = new int[3][3];
        currentPlayer = 1;
        winner = 0;
        turn = 0;
        gameEnded = false;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public int getWinner() {
        return winner;
    }

    public int getTurn() {
        return turn;
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

    public int[][] getBoard() {
        return board;
    }

    public void play(int x, int y) {
        //Arg checks:
        if (x < 0 || x > 2 || y < 0 || y > 2) {
            System.out.println("Invalid coordinates, please try again.");
            return;
        }

        if (gameEnded) {
            return;
        }

        //Game logic:
        //Check if the move is valid
        if (board[x][y] == 0) {
            board[x][y] = currentPlayer;
            turn++;
            if (checkWin(x, y)) {
                winner = currentPlayer;
                gameEnded = true;
                gameEnd();
            } else if (turn == 9) {
                gameEnded = true;
                gameEnd();
            } else {
                currentPlayer = currentPlayer == 1 ? 2 : 1;
            }
        }else{
            System.out.println("This slot is already taken, please try again.");
            return;
        }
    }

    private void gameEnd(){
        if(winner == 0){
            System.out.println("Game ended in a draw!");
            System.out.println(this.toString());
        }else{
            System.out.println("Player " + winner + " won the game!");
            System.out.println(this.toString());
        }
    }

    private boolean checkWin(int x, int y) {
        return (board[x][0] == board[x][1] && board[x][0] == board[x][2])
                || (board[0][y] == board[1][y] && board[0][y] == board[2][y])
                || (x == y && board[0][0] == board[1][1] && board[0][0] == board[2][2])
                || (x + y == 2 && board[0][2] == board[1][1] && board[0][2] == board[2][0]);
    }

    public void reset() {
        board = new int[3][3];
        currentPlayer = 1;
        winner = 0;
        turn = 0;
        gameEnded = false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Player ").append(currentPlayer).append(" turn\n");
        for (int i = 0; i < 3; i++) {
            sb.append("|");
            for (int j = 0; j < 3; j++) {
                sb.append(board[i][j] == 0 ? " " : board[i][j] == 1 ? "X" : "O").append("|");
            }
            sb.append("\n");
        }
        return sb.toString();
    }




}
