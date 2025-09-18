package command;

import model.Discipline;

import connect.ServerManager;

public class FilterGreaterThanDisciplineCommand implements ICommand {
    private ServerManager serverManager;
    private InputHelper inputHelper;

    public FilterGreaterThanDisciplineCommand(ServerManager serverManager, InputHelper inputHelper) {
        this.serverManager = serverManager;
        this.inputHelper = inputHelper;
    }

    @Override
    public void execute(String[] args, String username, String password) {
        Discipline discipline = inputHelper.readDiscipline();
        this.serverManager.sendCommand("filter_greater_than_discipline", null, discipline, (Number) null, username, password).printMessages();;
    }

    @Override
    public String getDescription() {
        return "вывести элементы, значение поля discipline которых больше заданного";
    }
}