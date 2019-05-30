package client;

import models.User;
import server.Commands;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ServerConnection {
    private static Socket socket;
    private static OutputStream outputStream;
    private static InputStream inputStream;
    private static Writer writer;
    private static Reader reader;
    private static Scanner scanner;
    private static PrintWriter out;

    static void connectServer() throws IOException {
        socket = new Socket("localhost", 8232);
        outputStream = socket.getOutputStream();
        inputStream = socket.getInputStream();
        writer = new OutputStreamWriter(outputStream);
        reader = new InputStreamReader(inputStream);
        out = new PrintWriter(writer);
        scanner = new Scanner(reader);
    }

    static void sendUser(User user) {
        out.println(Commands.CREATE_USER);
        out.println(user.toString());
        out.flush();
    }
}
