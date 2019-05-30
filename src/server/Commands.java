package server;

public enum Commands {
    CREATE_USER("Create User");

    String command;

    Commands(String command) {
        this.command = command;
    }

    @Override public String toString() {
        return command;
    }
}
