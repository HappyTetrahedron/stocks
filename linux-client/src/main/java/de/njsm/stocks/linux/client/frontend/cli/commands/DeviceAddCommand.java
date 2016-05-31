package de.njsm.stocks.linux.client.frontend.cli.commands;

import de.njsm.stocks.linux.client.Configuration;
import de.njsm.stocks.linux.client.data.Ticket;
import de.njsm.stocks.linux.client.data.UserDevice;
import de.njsm.stocks.linux.client.data.view.UserDeviceView;
import de.njsm.stocks.linux.client.exceptions.SelectException;
import de.njsm.stocks.linux.client.frontend.cli.InputReader;

import java.util.List;

public class DeviceAddCommand extends Command {

    public DeviceAddCommand(Configuration c) {
        this.c = c;
        this.command = "add";
        this.description = "Add a device";
    }

    @Override
    public void handle(List<String> commands) {
        if (commands.size() == 4){
            addDevice(commands.get(2), commands.get(3));
        } else {
            addDevice();
        }
    }

    public void addDevice() {
        System.out.print("Creating a new device\nName: ");
        String name = c.getReader().nextName();
        System.out.print("Who is the owner?  ");
        String user = c.getReader().next();
        addDevice(name, user);
    }

    public void addDevice(String name, String username) {
        try {
            int userId = UserCommand.selectUser(
                    c.getDatabaseManager().getUsers(username),
                    username);
            Ticket ticket;


            System.out.print("Create new device '" + name + "' for user '" +
                    username + "'? [y/N]  ");
            if (c.getReader().getYesNo()) {
                UserDevice d = new UserDevice();
                d.name = name;
                d.userId = userId;
                try {
                    ticket = c.getServerManager().addDevice(d);
                    (new RefreshCommand(c)).refreshDevices();

                    UserDeviceView[] devices = c.getDatabaseManager().getDevices(d.name);

                    System.out.println("Creation successful. The new device needs these parameters:");
                    System.out.println("\tUser name: " + username);
                    System.out.println("\tDevice name: " + name);
                    System.out.println("\tUser ID: " + userId);
                    System.out.println("\tDevice ID: " + devices[devices.length-1].id);
                    System.out.println("\tFingerprint: " + c.getFingerprint());
                    System.out.println("\tTicket: " + ticket.ticket);
                } catch (RuntimeException e) {
                    System.out.println("Creation failed. " + e.getMessage());
                }
            } else {
                System.out.println("Aborted.");
            }
        } catch (SelectException e) {
            System.out.println(e.getMessage());
        }


    }
}
