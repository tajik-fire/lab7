package command;

import connect.ServerManager;

/**
 * Класс InfoCommand реализует команду info для вывода информации о коллекции.
 */
public class InfoCommand implements ICommand {
    private ServerManager serverManager;

    public InfoCommand(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    @Override
    public void execute(String[] args, String username, String password) {
        this.serverManager.sendCommand("info", null, (model.Discipline) null, (Number) null, username, password).printMessages();
    }

    @Override
    public String getDescription() {
        return "вывести информацию о коллекции";
    }
}