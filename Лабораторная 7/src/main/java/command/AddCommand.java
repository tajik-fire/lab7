package command;

import model.LabWork;
import connect.ServerManager;

/**
 * Класс AddCommand реализует команду add для добавления нового элемента в коллекцию.
 */
public class AddCommand implements ICommand {
    private ServerManager serverManager;
    private InputHelper inputHelper;

    public AddCommand(ServerManager serverManager, InputHelper inputHelper) {
        this.serverManager = serverManager;
        this.inputHelper = inputHelper;
    }

    @Override
    public void execute(String[] args, String username, String password) {
        try {
            LabWork labWork = this.inputHelper.createLabWork();
            serverManager.sendCommand("add", labWork, (model.Discipline) null, (Number) null, username, password).printMessages();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "добавить новый элемент в коллекцию";
    }
}