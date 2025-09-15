package command;

import connect.ServerManager;

public class PrintAscendingCommand implements ICommand {
    private ServerManager serverManager;

    public PrintAscendingCommand(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    @Override
    public void execute(String[] args, String username, String password) {
        this.serverManager.sendCommand("print_ascending", null, (model.Discipline) null, (Number) null, username, password).printMessages();
    }

    @Override
    public String getDescription() {
        return "вывести элементы коллекции в порядке возрастания";
    }
}