package command;

import model.User;
import transport.Request;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HelpCommand implements ICommand {
    private final Map<String, ICommand> commands;

    public HelpCommand(Map<String, ICommand> commands) {
        this.commands = commands;
    }

    @Override
    public List<String> execute(Request request, User user) {
        return commands.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue().getDescription())
                .collect(Collectors.toList());
    }

    @Override
    public String getDescription() {
        return "вывести справку по доступным командам";
    }
}
