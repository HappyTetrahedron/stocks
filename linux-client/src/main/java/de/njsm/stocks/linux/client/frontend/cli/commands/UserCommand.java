package de.njsm.stocks.linux.client.frontend.cli.commands;

import de.njsm.stocks.linux.client.Configuration;
import de.njsm.stocks.linux.client.data.User;
import de.njsm.stocks.linux.client.frontend.cli.InputReader;

import java.util.LinkedList;
import java.util.List;

public class UserCommand extends Command {

    protected final CommandManager m;

    public UserCommand(Configuration c) {
        command = "user";
        description = "Manage the users of the stocks system";
        this.c = c;

        List<Command> commandList = new LinkedList<>();
        commandList.add(new UserAddCommand(c));
        commandList.add(new UserListCommand(c));
        commandList.add(new UserRemoveCommand(c));
        m = new CommandManager(commandList, "user");
    }

    @Override
    public void handle(List<String> commands) {
        if (commands.isEmpty()) {
            new UserListCommand(c).listUsers();
        } else {
            m.handleCommand(commands);
        }
    }

    @Override
    public void printHelp() {
        m.printHelp();
    }

    public static int selectUser(User[] users, String name) {
        InputReader scanner = new InputReader(System.in);
        int result;
        if (users.length == 1) {
            result = users[0].id;
        } else if (users.length == 0) {
            System.out.println("No such user found: " + name);
            result = -1;
        } else {
            System.out.println("Several users found");
            for (User u : users) {
                System.out.println("\t" + u.id + ": " + u.name);
            }
            System.out.print("Choose one (default " + users[0].id + "): ");
            result = scanner.nextInt(users[0].id);
        }
        return result;
    }
}
