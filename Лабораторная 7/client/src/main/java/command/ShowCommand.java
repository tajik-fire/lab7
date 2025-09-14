package command;

import connect.ServerManager;

/**
 * Класс ShowCommand реализует команду show для вывода всех элементов коллекции.
 */
public class ShowCommand implements ICommand {
    private ServerManager serverManager;

    public ShowCommand(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    @Override
    public void execute(String[] args, String username, String password) {
        this.serverManager.sendCommand("show", null, (model.Discipline) null, (Number) null, username, password).printMessages();;
    }

    @Override
    public String getDescription() {
        return "вывести все элементы коллекции";
    }
}