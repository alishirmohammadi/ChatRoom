package server;

import models.User;
import models.chat.Chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    public static Map<User, Connection> connections = new HashMap<>();
    public static Map<Integer, Chat> chats = new HashMap<>();
    public static final int CHAT_SERVER_PORT = 8232;
    public static final int HTTP_SERVER_PORT = 8231;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(CHAT_SERVER_PORT);
        httpServerThread.start();
        System.out.println("Chat Server started at port:" + CHAT_SERVER_PORT);
        while(true) {
            Socket socket = serverSocket.accept();
            new Connection(socket).start();
        }
    }

    private static Thread httpServerThread = new Thread(() -> {
        try {
            ServerSocket httpServerSocket = new ServerSocket(HTTP_SERVER_PORT);
            System.out.println("Http Server started at port:" + CHAT_SERVER_PORT);
            while (true) {
                Socket socket = httpServerSocket.accept();
                System.out.println("Http server client connected.");
                new HttpServer(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

    });
}
