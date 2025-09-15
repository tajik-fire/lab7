package command;

import connect.ServerManager;

public class PrintFieldDescendingMinimalPointCommand implements ICommand {
    private ServerManager serverManager;

    public PrintFieldDescendingMinimalPointCommand(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    @Override
    public void execute(String[] args, String username, String password) {
        this.serverManager.sendCommand("print_field_descending_minimal_point", null, (model.Discipline) null, (Number) null, username, password).printMessages();
    }

    @Override
    public String getDescription() {
        return "вывести значения поля minimalPoint всех элементов в порядке убывания";
    }
}