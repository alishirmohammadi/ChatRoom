package server;

public enum Commands {
    CREATE_USER("Create User"),
    SEND_MESSAGE("Send Message"),
    SET_PROFILE("Set Profile"),
    RECEIVE_MESSAGE("Receive Message"),
    SEND_IMAGE("Send Image"),
    CREATE_GROUP("Create Group"),
    ADD_CHATS("Add Chats");

    String command;

    Commands(String command) {
        this.command = command;
    }

    @Override public String toString() {
        return command;
    }
}
