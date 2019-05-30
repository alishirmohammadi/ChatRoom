package server;

import models.User;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    public static Map<User, Connection> connections = new HashMap<>();
    public static final int PORT = 8232;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server started. localhost:" + PORT);
        while(true) {
            Socket socket = serverSocket.accept();
            new Connection(socket).start();
        }
    }
}
