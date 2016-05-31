package de.njsm.stocks.linux.client.frontend.cli;

import de.njsm.stocks.linux.client.Configuration;
import de.njsm.stocks.linux.client.frontend.MainHandler;
import de.njsm.stocks.linux.client.frontend.cli.commands.*;

import java.util.*;


public class CliMainHandler implements MainHandler {

    protected final CommandManager m;
    protected final Configuration c;

    public CliMainHandler(Configuration c) {
        this.c = c;

        ArrayList<CommandHandler> commandHandler = new ArrayList<>();
        commandHandler.add(new AddCommandHandlerHandler(c));
        commandHandler.add(new EatCommandHandler(c));
        commandHandler.add(new FoodCommandHandler(c));
        commandHandler.add(new LocationCommandHandler(c));
        commandHandler.add(new RefreshCommandHandler(c));
        commandHandler.add(new UserCommandHandler(c));
        commandHandler.add(new DeviceCommandHandler(c));

        m = new CommandManager(commandHandler);
    }

    @Override
    public void run(String[] args) {
        boolean endRequested = false;

        if (args.length > 0) {
            m.handleCommand(parseCommand(args));
        } else {
            while (!endRequested) {
                String command = c.getReader().next("stocks $ ");

                switch (command) {
                    case "quit":
                        endRequested = true;
                        break;
                    case "":
                        break;
                    default:
                        forwardCommand(command);
                }
            }
        }
        c.getReader().shutdown();
    }

    public void forwardCommand(String command) {
        List<String> commandList = parseCommand(command);
        m.handleCommand(commandList);
    }

    public List<String> parseCommand(String command) {
        String[] commands = command.split(" ");
        LinkedList<String> result = new LinkedList<>();
        Collections.addAll(result, commands);
        return result;
    }

    public List<String> parseCommand(String[] commandArray) {
        LinkedList<String> result = new LinkedList<>();
        Collections.addAll(result, commandArray);
        return result;
    }
}
