package command;

import connect.ServerManager;
import transport.Response;

import java.util.List;

public class LoginCommand implements ICommand {
    private final ServerManager serverManager;

    public LoginCommand(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    @Override
    public void execute(String[] args, String username, String password) {
        // The username and password are now passed directly from ConsoleUI
        // No need to parse from args for login/register commands

        Response response = serverManager.sendCommand("login", null, (model.Discipline) null, (Number) null, username, password);
        response.printMessages();
    }

    @Override
    public String getDescription() {
        return "авторизоваться в системе";
    }
}
