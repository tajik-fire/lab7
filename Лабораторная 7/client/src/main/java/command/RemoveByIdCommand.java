package command;

import connect.ServerManager;

/**
 * Класс RemoveByIdCommand реализует команду remove_by_id для удаления элемента коллекции по id.
 */
public class RemoveByIdCommand implements ICommand {
    private ServerManager serverManager;

    public RemoveByIdCommand(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    @Override
    public void execute(String[] args, String username, String password) {
        if (args.length < 1) {
            System.out.println("Не указан id элемента для удаления");
            return;
        }

        long id = Long.parseLong(args[0]);
        this.serverManager.sendCommand("remove_by_id", null, (model.Discipline) null, (Number) id, username, password).printMessages();
    }

    @Override
    public String getDescription() {
        return "удалить элемент из коллекции по его id";
    }
}