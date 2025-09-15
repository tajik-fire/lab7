package command;

import java.util.Map;

/**
 * Класс HelpCommand реализует команду help для вывода справки по доступным командам.
 */
public class HelpCommand implements ICommand {
    private Map<String, ICommand> commands;

    public HelpCommand(Map<String, ICommand> commands) {
        this.commands = commands;
    }

    @Override
    public void execute(String[] args, String username, String password) {
        System.out.println("Доступные команды:");
        for (Map.Entry<String, ICommand> entry : commands.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue().getDescription());
        }
    }

    @Override
    public String getDescription() {
        return "вывести справку по доступным командам";
    }
}