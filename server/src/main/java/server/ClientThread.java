package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread{
    private Socket socket = null;
    private BufferedReader in;
    private int[] numberOfClients;

    public ClientThread(Socket socket, int[] numberOfClients) {
        this.socket = socket;
        this.numberOfClients = numberOfClients;
    }

    public void run() {
        try {
                this.getRequest();
                this.sendResponse();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void getRequest() throws IOException {
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    private void sendResponse() throws IOException {
        String request = in.readLine();
        System.out.println("Server received the request: '" + request + "'");
        if (request == null) {
            return;
        }
        PrintWriter out = new PrintWriter(socket.getOutputStream());

        switch (request) {
            case "stop" -> {
                out.println("server-stop");
                out.close();
                out.flush();
                System.out.println("Connection terminated!");
                System.exit(0);
            }
            case "exit" -> {
                out.println("successfully disconnected");
                out.flush();
                System.out.println("Client disconnected");
                numberOfClients[0]--;
            }
            case "new_stop" -> {
                out.println("server will stop when all the clients are disconnected");
                out.flush();
                if(numberOfClients[0] == 0) {
                    out.close();
                    System.exit(1);
                }
            }
            default -> {
                out.println("bad-request");
            }
        }
        out.flush();
    }
}
