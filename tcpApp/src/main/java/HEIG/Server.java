package HEIG;
import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
/**
 * This class modelizes our server object for our TCP application protocol.
 * It can accept multiple clients and dispatch them to threads via ClientHandler objects.
 */
public class Server implements Runnable{

    private int PORT = 1234; //default port
    private int THREAD_POOL_SIZE = 2; //default number of threads
    private int SERVER_ID = 0; //default server id

    public Server(int port, int threads, int serverId){
        this.PORT = port;
        this.THREAD_POOL_SIZE = threads;
        this.SERVER_ID = serverId;
    }

    /**
     * This method starts the server and waits for clients to connect, then dispatches them to a thread via
     * a ClientHandler object. (see ClientHandler.java)
     */
    @Override
    public void run(){
        int activeThreads = 0;
        try(ServerSocket serverSocket = new ServerSocket(PORT);){
            System.out.println("[Server: " + SERVER_ID + "] : starting");
            System.out.println("[Server: " + SERVER_ID + "] : listening on port " + PORT);

            TicTacToe game = new TicTacToe();
            Thread[] players = new Thread[2];

            // Infinite loop, limited by the number of threads available in the pool
            while(activeThreads <= THREAD_POOL_SIZE){
                Socket socket = serverSocket.accept();
                System.out.println("Server : new client connected");
                players[activeThreads] = new Thread(new ClientHandler(socket, game, activeThreads + 1));
                activeThreads++;
                if(activeThreads == 1){
                    System.out.println("Server : waiting for another player to connect");
                }
                else {
                    System.out.println("Server : starting game");
                    players[0].start();
                    players[1].start();
                    activeThreads = 0;
                    break;
                }
            }
            while(true){
                //If one of the threads died, we disconnect the other one and shut down the server
                if(!players[0].isAlive() || !players[1].isAlive()){
                    System.out.println("Server : one of the players disconnected, shutting down");
                    players[0].interrupt();
                    players[1].interrupt();
                    return;
                }
            }
        } catch (IOException e) {
            System.out.println("Server : IO error " + e.getMessage());
        }
    }
}
