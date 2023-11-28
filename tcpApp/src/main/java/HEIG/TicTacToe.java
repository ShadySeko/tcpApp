package HEIG;

/**
 * This class modelizes a TicTacToe game.
 */
public class TicTacToe {

    private int[][] board;
    private int currentPlayer = 1;
    private int winner;
    private int turn;
    private boolean gameEnded;

    public TicTacToe() {
        board = new int[3][3];
        winner = 0;
        turn = 0;
        gameEnded = false;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
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


    /**
     * Core method of the game, plays a move on the board at the given coordinates.
     * also performs checks to see if the game is over etc.
     * @param x x coordinate
     * @param y y coordinate
     */
    public void play(int x, int y) {
        //Arg checks:

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

    /**
     * This method prints the game result to the console.
     */
    private void gameEnd(){
        if(winner == 0){
            System.out.println("Game ended in a draw!");
            System.out.println(this.toString());
        }else{
            System.out.println("Player " + winner + " won the game!");
            System.out.println(this.toString());
        }
    }

    /**
     * This method checks if the game is over.
     * @param x x coordinate
     * @param y y coordinate
     * @return true if the game is over, false otherwise
     */
    private boolean checkWin(int x, int y) {
        return (board[x][0] == board[x][1] && board[x][0] == board[x][2])
                || (board[0][y] == board[1][y] && board[0][y] == board[2][y])
                || (x == y && board[0][0] == board[1][1] && board[0][0] == board[2][2])
                || (x + y == 2 && board[0][2] == board[1][1] && board[0][2] == board[2][0]);
    }

    /**
     * This method resets the game.
     */
    public void reset() {
        board = new int[3][3];
        currentPlayer = 1;
        winner = 0;
        turn = 0;
        gameEnded = false;
    }

    /**
     * This method returns a string representation of the game board, for visualization purposes.
     * This will be sent to the client as visual representation of the game board.
     * @return
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
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
