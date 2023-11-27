package HEIG;

import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * This class modelizes a handler for clients of our multithreaded TCP application protocol.
 */
public class ClientHandler implements Runnable {

    private final Socket socket; //The socket to communicate with the client

    private final String EOT = "\u0004"; //End of transmission character

    private final TicTacToe game;
    private int playerNumber;

    public ClientHandler(Socket socket, TicTacToe game, int playerNumber) {
        this.socket = socket;
        this.game = game;
        this.playerNumber = playerNumber;
    }

    /**
     * This method is the core loop of our client handler.
     * It reads the input from the client, and sends back the appropriate response.
     */
    @Override
    public void run() {
        try(
                socket;
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
        ) {
            /**
             * The server is a TicTacToe game. Here we print a welcome message with the available commands. Write Tic Tac Toe in ascii art.
             */


            out.write("Server : Welcome to the Tic Tac Toe game!");
            out.newLine();
            out.write("Server : Here are the available commands :");
            out.newLine();
            out.write("Server : PLAY <x> <y> : play a move at the given coordinates");
            out.newLine();
            out.write("Server : RESET : reset the game");
            out.write("Server : QUIT : quit the game");
            out.newLine();
            out.write("Server : HELP : display this help message");
            out.newLine();
            out.write("Server : You are player " + playerNumber + "!");
            out.newLine();


            String input;


            //TODO: Server logic here
            while(true){

                //if current player is not the playerNumber, wait for the other player to play
                if(game.getCurrentPlayer() != this.playerNumber){
                    out.write("WAITING FOR OTHER PLAYER TO PLAY ");
                    out.newLine();
                    out.flush();
                    while(game.getCurrentPlayer() != this.playerNumber){
                        Thread.sleep(1000);
                    }
                    out.write(game.toString());
                    out.newLine();
                    out.flush();
                }

                out.write("YOUR TURN ");
                out.newLine();
                out.write(EOT);
                out.newLine();
                out.flush();

                input = in.readLine();


                if(game.isGameEnded()){
                    out.write("GAME OVER, PLAYER " + game.getWinner() + " WON!");
                    out.newLine();
                    out.write(EOT);
                    out.newLine();
                    out.flush();
                    break;
                }
                String[] inputArray = input.split(" ");

                switch(inputArray[0]){
                    case "PLAY":
                        if(inputArray.length != 3){
                            out.write("Server : invalid command, please try again.");
                            out.newLine();
                            out.write(EOT);
                            out.newLine();
                            out.flush();
                            break;
                        }else if(game.isGameEnded()){
                            out.write("Server : game is already over, please reset the game.");
                            out.newLine();
                            out.write(EOT);
                            out.newLine();
                            out.flush();
                            break;
                        }else if(Integer.parseInt(inputArray[1]) < 0 || Integer.parseInt(inputArray[1]) > 2 || Integer.parseInt(inputArray[2]) < 0 || Integer.parseInt(inputArray[2]) > 2){
                            out.write("Server : invalid move, please try again.");
                            out.newLine();
                            out.write(EOT);
                            out.newLine();
                            out.flush();
                            break;
                        }
                        game.setCurrentPlayer(playerNumber);
                        int x = Integer.parseInt(inputArray[1]);
                        int y = Integer.parseInt(inputArray[2]);
                        game.play(x, y);
                        out.write("Server : move played");
                        out.newLine();
                        out.write(game.toString());
                        out.newLine();
                        out.flush();
                        if(playerNumber == 1){
                            game.setCurrentPlayer(2);
                        }else{
                            game.setCurrentPlayer(1);
                        }
                        break;
                    case "QUIT":
                        out.write("Server : closing connection, see you soon!");
                        out.newLine();
                        out.write("SHUTDOWN");
                        out.newLine();
                        out.flush();
                        return;
                        
                    case "HELP":
                        out.write("Server : Here are the available commands :");
                        out.newLine();
                        out.write("Server : PLAY <x> <y> : play a move at the given coordinates");
                        out.newLine();
                        out.write("Server : QUIT : quit the game");
                        out.newLine();
                        out.write("Server : RESET : reset the game");
                        out.newLine();
                        out.write("Server : HELP : display this help message");
                        out.newLine();
                        out.write(EOT);
                        out.newLine();
                        out.flush();
                        break;

                    case "RESET":
                        game.reset();
                        out.write("Server : game reset");
                        out.newLine();
                        out.write(EOT);
                        out.newLine();
                        out.flush();
                        break;

                    default:
                        out.write("Server : invalid command, please try again.");
                        out.newLine();
                        out.write(EOT);
                        out.newLine();
                        out.flush();
                        break;
                }
            }


    } catch (IOException e) {
        System.out.println("Server : IO error " + e.getMessage());
    } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void playerAlert(BufferedWriter out) throws IOException {
        if(game.getCurrentPlayer() != this.playerNumber){
            out.write("Server : waiting for other player to play, no commands active until opponent plays");
            out.newLine();
            out.write(EOT);
        }else{
            out.write("Server : your turn to play, please enter a command:");
            out.newLine();
            out.write(EOT);
        }
        out.newLine();
        out.flush();
    }
}
