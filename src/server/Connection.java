package server;

import com.google.gson.Gson;
import models.User;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Connection extends Thread {
    private static Gson gson = new Gson();
    private Socket socket;
    private Scanner scanner;
    private PrintWriter out;

    private User user;

    public Connection(Socket clientSocket) throws IOException {
        this.socket = clientSocket;
        InputStream inputStream = socket.getInputStream();
        OutputStream writerStream = socket.getOutputStream();
        Writer writer = new OutputStreamWriter(writerStream);
        Reader reader = new InputStreamReader(inputStream);
        this.scanner = new Scanner(reader);
        this.out = new PrintWriter(writer);
    }

    @Override public void run() {
        while (true) {
            if(scanner.hasNext()) {
                String command = scanner.nextLine();
                if(command.equals(Commands.CREATE_USER.toString())) {
                    String userJson = scanner.nextLine();
                    user = gson.fromJson(userJson, User.class);
                    System.out.format("User '%s' connected.\n", user.getName());
                }
            }
        }
    }

    public User getUser() {
        return user;
    }
}
