package command;

import connect.ServerManager;

public class ReorderCommand implements ICommand {
    private ServerManager serverManager;

    public ReorderCommand(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    @Override
    public void execute(String[] args, String username, String password) {
        this.serverManager.sendCommand("reorder", null, (model.Discipline) null, (Number) null, username, password).printMessages();
    }

    @Override
    public String getDescription() {
        return "отсортировать коллекцию в порядке, обратном нынешнему";
    }
}