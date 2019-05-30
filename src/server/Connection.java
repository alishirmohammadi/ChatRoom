package server;

import com.google.gson.Gson;
import models.User;
import models.chat.Chat;
import models.chat.PrivateChat;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

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
                    user.setId(Server.connections.size() + 1);
                    addDefaultChats();
                    Server.connections.put(user, this);
                    System.out.format("User '%s' connected.\n", user.getName());
                }
            }
        }
    }

    public void addChats(List<Chat> chats) {
        user.getChats().addAll(chats);
        out.println(Commands.ADD_CHATS);
        out.println(gson.toJson(chats));
        out.flush();
    }

    public void addDefaultChats() {
        List<Chat> chats = Server.connections.keySet().stream()
                .map(PrivateChat::new)
                .collect(Collectors.toList());
        addChats(chats);
    }

    public User getUser() {
        return user;
    }
}
