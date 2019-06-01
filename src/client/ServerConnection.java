package client;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import controllers.ChatController;
import javafx.application.Platform;
import models.User;
import models.chat.Chat;
import models.chat.PrivateChat;
import models.message.Message;
import server.Commands;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.*;

public class ServerConnection extends Thread {
    private static YaGson yaGson = new YaGson();
    private Socket socket;
    private Scanner scanner;
    private PrintWriter out;

    public void connectServer() throws IOException {
        socket = new Socket("localhost", 8232);
        OutputStream outputStream = socket.getOutputStream();
        InputStream inputStream = socket.getInputStream();
        Writer writer = new OutputStreamWriter(outputStream);
        Reader reader = new InputStreamReader(inputStream);
        out = new PrintWriter(writer);
        scanner = new Scanner(reader);
    }

    @Override public void run() {
        while (true) {
            if(scanner.hasNext()) {
                String command = scanner.nextLine();
                if(command.equals(Commands.ADD_CHATS.toString())) {
                    String chatsJson = scanner.nextLine();
                    System.out.println("new chats added: " + chatsJson);
                    Type chatsType = new TypeToken<ArrayList<PrivateChat>>() {}.getType();
                    List<PrivateChat> chats = yaGson.fromJson(chatsJson, chatsType);
                    Main.user.getChats().addAll(chats);
                    if(ChatController.instance != null)
                        Platform.runLater(() -> ChatController.instance.updateChats());
                } else if(command.equals(Commands.RECEIVE_MESSAGE.toString())) {
                    String messageJson = scanner.nextLine();
                    Message message = yaGson.fromJson(messageJson, Message.class);
                    Chat messageChat = null;
                    for(Chat chat : Main.user.getChats())
                        if(chat.getId() == message.getChatId())
                            messageChat = chat;
                    messageChat.getMessages().add(0, message);
                    Chat finalMessageChat = messageChat;
                    Platform.runLater(() -> {
                        ChatController.instance.showMessage(message, finalMessageChat);
                    });
                }
            }
        }
    }

    public void createGroup(String Name, String members) {
        out.println(Commands.CREATE_GROUP);
        out.println(Name);
        out.println(members);
        out.flush();
    }

    public void sendUser(User user) {
        out.println(Commands.CREATE_USER);
        out.println(user.toString());
        out.flush();
    }

    public void setProfile(File imageFile) throws IOException {
        out.println(Commands.SET_PROFILE);
        out.flush();
        sendImage(imageFile);
    }

    public boolean sendMessage(Message message) {
        out.println(Commands.SEND_MESSAGE);
        out.println(message.toString());
        out.flush();
        return true;
    }

    public void sendImage(File imageFile) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        BufferedImage image = ImageIO.read(imageFile);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", byteArrayOutputStream);
        byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
        outputStream.write(size);
        outputStream.write(byteArrayOutputStream.toByteArray());
        outputStream.flush();
    }

    public void sendImage(File image, Chat chat) throws IOException {
        out.println(Commands.SEND_IMAGE);
        out.println(chat.toString());
        out.flush();
        sendImage(image);
    }
}
