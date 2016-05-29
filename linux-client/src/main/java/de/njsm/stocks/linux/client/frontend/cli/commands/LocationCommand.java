package de.njsm.stocks.linux.client.frontend.cli.commands;

import de.njsm.stocks.linux.client.Configuration;
import de.njsm.stocks.linux.client.data.Location;
import de.njsm.stocks.linux.client.exceptions.SelectException;
import de.njsm.stocks.linux.client.frontend.cli.InputReader;

import java.util.ArrayList;
import java.util.List;

public class LocationCommand extends Command {

    protected final CommandManager m;

    public LocationCommand(Configuration c) {
        command = "loc";
        description = "Manage the locations to store food";
        this.c = c;

        List<Command> commands = new ArrayList<>();
        commands.add(new LocationAddCommand(c));
        commands.add(new LocationListCommand(c));
        commands.add(new LocationRenameCommand(c));
        commands.add(new LocationRemoveCommand(c));
        m = new CommandManager(commands, command);
    }

    @Override
    public void handle(List<String> commands) {
        if (commands.isEmpty()) {
            new LocationListCommand(c).listLocations();
        } else {
            m.handleCommand(commands);
        }
    }

    @Override
    public void printHelp() {
        m.printHelp();
    }

    public static int selectLocation(Location[] l, String name) throws SelectException {
        InputReader scanner = new InputReader(System.in);
        int result;

        if (l.length == 1) {
            result = l[0].id;
        } else if (l.length == 0) {
            throw new SelectException("No such location found: " + name);
        } else {
            System.out.println("Several locations found");
            for (Location loc : l) {
                System.out.println("\t" + loc.id + ": " + loc.name);
            }
            System.out.print("Choose one (default " + l[0].id + "): ");
            result = scanner.nextInt(l[0].id);
        }
        return result;
    }
}
