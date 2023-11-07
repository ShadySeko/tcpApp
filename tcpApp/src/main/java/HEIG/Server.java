package HEIG;
import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
/**
 * This class modelizes our server object for our TCP application protocol.
 */
public class Server implements Runnable{

    private int PORT = 1234; //default port
    private int THREAD_POOL_SIZE = 0; //default number of threads
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

            // Infinite loop, limited by the number of threads available in the pool

            while(activeThreads <= THREAD_POOL_SIZE){
                Socket socket = serverSocket.accept();
                System.out.println("Server : new client connected");
                Thread clientThread = new Thread(new ClientHandler(socket));
                clientThread.start();
                activeThreads++;
            }
            System.out.println("Server : thread pool is full, refusing new connections");
            throw new IllegalArgumentException("Server : thread pool is full, refusing new connections");

        } catch (IOException e) {
            System.out.println("Server : IO error " + e.getMessage());
        }
    }
}
