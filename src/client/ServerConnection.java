package client;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import controllers.ChatController;
import models.User;
import models.chat.PrivateChat;
import server.Commands;
import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ServerConnection extends Thread {
    private static YaGson yaGson = new YaGson();
    private Socket socket;
    private OutputStream outputStream;
    private InputStream inputStream;
    private Writer writer;
    private Reader reader;
    private Scanner scanner;
    private PrintWriter out;

    public void connectServer() throws IOException {
        socket = new Socket("localhost", 8232);
        outputStream = socket.getOutputStream();
        inputStream = socket.getInputStream();
        writer = new OutputStreamWriter(outputStream);
        reader = new InputStreamReader(inputStream);
        out = new PrintWriter(writer);
        scanner = new Scanner(reader);
    }

    @Override public void run() {
        while (true) {
            if(scanner.hasNext()) {
                String command = scanner.nextLine();
                if(command.equals(Commands.ADD_CHATS.toString())) {
                    String chatsJson = scanner.nextLine();
                    Type chatsType = new TypeToken<ArrayList<PrivateChat>>() {}.getType();
                    List<PrivateChat> chats = yaGson.fromJson(chatsJson, chatsType);
                    Main.user.getChats().addAll(chats);
                    if(ChatController.instance != null)
                        ChatController.instance.updateChats();
                }
            }
        }
    }

    public void sendUser(User user) {
        out.println(Commands.CREATE_USER);
        out.println(user.toString());
        out.flush();
    }
}
