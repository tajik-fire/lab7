package command;

import connect.ServerManager;
import model.LabWork;

/**
 * Класс UpdateCommand реализует команду update для обновления элемента коллекции по id.
 */
public class UpdateCommand implements ICommand {
    private ServerManager serverManager;
    private InputHelper inputHelper;

    public UpdateCommand(ServerManager serverManager, InputHelper inputHelper) {
        this.serverManager = serverManager;
        this.inputHelper = inputHelper;
    }

    @Override
    public void execute(String[] args, String username, String password) {
        if (args.length < 1) {
            System.out.println("Не указан id элемента для обновления");
            return;
        }

        try {
            long id = Long.parseLong(args[0]);
            LabWork labWork = inputHelper.createLabWork();
            this.serverManager.sendCommand("update", labWork, (model.Discipline) null, (Number) id, username, password).printMessages();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "обновить значение элемента коллекции по его id";
    }
}