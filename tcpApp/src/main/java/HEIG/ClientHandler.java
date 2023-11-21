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

    private TicTacToe game;

    public ClientHandler(Socket socket, TicTacToe game) {
        this.socket = socket;
        this.game = game;
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
            out.write("Server : You are player " + game.getCurrentPlayer() + "!");
            out.newLine();
            out.write(EOT);
            out.flush();

            String input;

            //TODO: Server logic here
            while(!(input = in.readLine()).equals(EOT)){
                String[] inputArray = input.split(" ");
                switch(inputArray[0]){
                    case "PLAY":
                        game.play(Integer.parseInt(inputArray[1]), Integer.parseInt(inputArray[2]));
                        break;
                    case "QUIT":
                        out.write("Server : closing connection, see you soon!");
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
                        break;

                    case "RESET":
                        game.reset();
                        out.write("Server : game reset");
                        break;

                    default:
                        out.write("Server : invalid command, please try again.");
                        out.newLine();
                        break;
                }
            }


    } catch (IOException e) {
        System.out.println("Server : IO error " + e.getMessage());
    }
    }
}
