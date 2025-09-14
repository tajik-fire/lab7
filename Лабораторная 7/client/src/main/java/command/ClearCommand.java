package command;

import connect.ServerManager;

/**
 * Класс ClearCommand реализует команду clear для очистки коллекции.
 */
public class ClearCommand implements ICommand {
    private ServerManager serverManager;

    public ClearCommand(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    @Override
    public void execute(String[] args, String username, String password) {
        serverManager.sendCommand("clear", null, (model.Discipline) null, (Number) null, username, password).printMessages();
    }

    @Override
    public String getDescription() {
        return "очистить коллекцию";
    }
}