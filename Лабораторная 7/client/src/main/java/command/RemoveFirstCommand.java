package command;

import connect.ServerManager;

/**
 * Класс RemoveFirstCommand реализует команду remove_first для удаления первого элемента коллекции.
 */
public class RemoveFirstCommand implements ICommand {
    private ServerManager serverManager;

    public RemoveFirstCommand(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    @Override
    public void execute(String[] args, String username, String password) {
        this.serverManager.sendCommand("remove_first", null, (model.Discipline) null, (Number) null, username, password).printMessages();
    }

    @Override
    public String getDescription() {
        return "удалить первый элемент из коллекции";
    }
}