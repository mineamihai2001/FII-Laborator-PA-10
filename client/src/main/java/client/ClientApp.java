package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;

public class ClientApp extends Thread {
    private final String serverAddress;
    private final int PORT;

    private Socket socket;

    // For responses coming from the server
    BufferedReader input;

    public ClientApp(String serverAddress, int PORT) {
        this.serverAddress = serverAddress;
        this.PORT = PORT;
    }

    public void connect() throws IOException {
        socket = new Socket(serverAddress, PORT);

        PrintWriter output = new PrintWriter(socket.getOutputStream());
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String request = null;
        BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));

        do {
            System.out.print("Input command: ");

                request = consoleInput.readLine();
            // System.out.println("Read the request: '" + request + "'");

            output.println(request);
            output.flush();
            if(!Objects.equals(request, "exit")) {
                getResponse();
            }
        } while (!request.equals("exit"));
    }

    private void getResponse() throws IOException {
        String response = input.readLine();
        System.out.println("Server response: '" + response + "'!");
    }
}
