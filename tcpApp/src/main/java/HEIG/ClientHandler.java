package HEIG;

import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * This class modelizes a handler for clients of our multithreaded TCP application protocol.
 */
public class ClientHandler implements Runnable {

    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
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



    } catch (IOException e) {
        System.out.println("Server : IO error " + e.getMessage());
    }
    }
}
