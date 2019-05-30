package server;

public enum Commands {
    CREATE_USER("Create User"),
    ADD_CHATS("Add Chats");

    String command;

    Commands(String command) {
        this.command = command;
    }

    @Override public String toString() {
        return command;
    }
}
