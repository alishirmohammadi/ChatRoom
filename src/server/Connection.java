package server;

import com.gilecode.yagson.YaGson;
import models.User;
import models.chat.Chat;
import models.chat.Group;
import models.chat.PrivateChat;
import models.message.Message;
import models.message.TextMessage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Connection extends Thread {
    private static YaGson yaGson = new YaGson();
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

    @Override
    public void run() {
        while (true) {
            if (scanner.hasNext()) {
                String command = scanner.nextLine();
                if (command.equals(Commands.CREATE_USER.toString())) {
                    String userJson = scanner.nextLine();
                    user = yaGson.fromJson(userJson, User.class);
                    user.setId(Server.connections.size() + 1);
                    addDefaultChats();
                    Server.connections.put(user, this);
                    System.out.format("User '%s' connected.\n", user.getName());
                } else if (command.equals(Commands.SEND_MESSAGE.toString())) {
                    String messageJson = scanner.nextLine();
                    Message message = yaGson.fromJson(messageJson, Message.class);
                    Chat chat = Server.chats.get(message.getChatId());
                    chat.getMessages().add(0, message);
                    if (user.getChats().contains(chat)) {
                        out.println("true");
                        out.flush();
                        if (chat instanceof PrivateChat) {
                            User receiver = ((PrivateChat) chat).getUser();
                            Chat receiverChat = receiver.getChats().stream().filter(
                                    chat1 -> chat1 instanceof PrivateChat
                            ).filter(
                                    chat1 -> ((PrivateChat) chat1).getUser().equals(user)
                            ).findFirst().get();
                            message.setChatId(receiverChat.getId());
                            receiverChat.getMessages().add(0, message);
                            Connection receiverConnection = Server.connections.get(receiver);
                            receiverConnection.sendMessage(message);
                            if (message instanceof TextMessage)
                                System.out.format("User '%s' sent '%s' to user '%s'\n", this.user.getName(), ((TextMessage) message).getText(), receiver.getName());
                        } else if (chat instanceof Group) {
                            // TODO: implement
                        }
                    } else {
                        out.println("false");
                        out.flush();
                    }
                } else if (command.equals(Commands.SET_PROFILE.toString())) {
                    try {
                        String fileName = receiveImage();
                        System.out.println(fileName);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static final String ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvwxyz0123456789";

    public static String randomFileName(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }


    public String receiveImage() throws IOException {
        byte[] sizeAr = new byte[4];
        InputStream inputStream = socket.getInputStream();
        inputStream.read(sizeAr);
        int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();

        byte[] imageAr = new byte[size];
        inputStream.read(imageAr);

        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));

        System.out.println("Received " + image.getHeight() + "x" + image.getWidth() + ": " + System.currentTimeMillis());

        String fileName = randomFileName(20) + ".jpg";
        ImageIO.write(image, "jpg", new File(fileName));
        return fileName;
    }

    public void sendMessage(Message message) {
        out.println(Commands.RECEIVE_MESSAGE);
        out.println(message.toString());
        out.flush();
    }

    public void addChats(List<Chat> chats) {
        user.getChats().addAll(chats);
        out.println(Commands.ADD_CHATS);
        out.println(yaGson.toJson(chats));
        out.flush();
    }

    public void addDefaultChats() {
        List<Chat> chats = new ArrayList<>();
        Server.connections.forEach((oldUser, connection) -> {
            PrivateChat newUserChat = new PrivateChat(oldUser);
            PrivateChat oldUserChat = new PrivateChat(this.user);
            newUserChat.setId(Server.chats.size());
            Server.chats.put(newUserChat.getId(), newUserChat);
            oldUserChat.setId(Server.chats.size());
            Server.chats.put(oldUserChat.getId(), oldUserChat);
            chats.add(newUserChat);
            connection.addChats(Collections.singletonList(oldUserChat));
        });
        addChats(chats);
    }

    public User getUser() {
        return user;
    }
}
