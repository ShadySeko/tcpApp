package HEIG;

import javax.rmi.ssl.SslRMIClientSocketFactory;
import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
/**
 * This class modelizes our client four our TCP application protocol.
 */
public class Client implements Runnable {

    private String host_address = "localhost"; //default host address
    private int host_port = 1234; //default host port
    private final String EOT = "\u0004";

    public Client(String serverAddress, int serverPort){
        this.host_address = serverAddress;
        this.host_port = serverPort;
    }

    /**
     * This method starts our client and connects to the server.
     */
    @Override
    public void run() {
        try(Socket socket = new Socket(host_address, host_port);
            BufferedReader inp = new BufferedReader(
                    new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8)
            );
            BufferedWriter outp = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8)
            );
            ){
            System.out.println("Client : connected to server " + host_address + " on port " + host_port);

            //Here we implement the client loop:

            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            String input;
            String output;
            System.out.println(inp.readLine());
            System.out.println("Server waiting for input:");
            while(!(input = userInput.readLine()).equals(EOT)){
                outp.write(input);
                outp.newLine(); //Add a newline to the end of the message
                outp.flush(); //Flush the buffer
                System.out.println("Client : sent message to server");
                //Sleep for a couple seconds to let the server process the message
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String response = inp.readLine(); //Read the response from the server
                System.out.println("Client : received response from server : " + "\n" + "> " + response);
            }

        } catch (Exception e) {
            System.out.println("Client : IO error " + e.getMessage());
        }

    }
}
