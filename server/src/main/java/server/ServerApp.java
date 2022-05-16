package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp {
    public static final int PORT = 8100;
    private int[] numberOfClients = {0};

    public ServerApp() throws IOException {
        ServerSocket serverSocket = null;
        try {
            System.out.println("waiting for clients...");
            serverSocket = new ServerSocket(PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                numberOfClients[0]+=1;
                System.out.println("Total clients: " + numberOfClients[0]);
                ClientThread clientThread = new ClientThread(socket, numberOfClients);
                clientThread.start();

                System.out.println("Connected clients " + numberOfClients[0]);

//                new ClientThread(socket, numberOfClients).start();

            }

        } catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        } finally {
            serverSocket.close();
        }
    }
}
