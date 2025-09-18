package command;

import repository.ILabWorkRepository;
import service.AuthService;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private Map<String, ICommand> commands = new HashMap<>();

    public CommandFactory(ILabWorkRepository repository, AuthService authService) {
        // Регистрация всех команд
        commands.put("help", new HelpCommand(commands)); // Help command does not interact with repository directly
        commands.put("info", new InfoCommand(repository));
        commands.put("show", new ShowCommand(repository));
        commands.put("add", new AddCommand(repository));
        commands.put("update", new UpdateCommand(repository));
        commands.put("remove_by_id", new RemoveByIdCommand(repository));
        commands.put("clear", new ClearCommand(repository));
        commands.put("remove_first", new RemoveFirstCommand(repository));
        commands.put("reorder", new ReorderCommand(repository));
        commands.put("sort", new SortCommand(repository));
        commands.put("filter_greater_than_discipline", new FilterGreaterThanDisciplineCommand(repository));
        commands.put("print_ascending", new PrintAscendingCommand(repository));
        commands.put("print_field_descending_minimal_point", new PrintFieldDescendingMinimalPointCommand(repository));
        commands.put("register", new RegisterCommand(authService));
        commands.put("login", new LoginCommand(authService)); 
    }

    public ICommand createCommand(String commandName) {
        return commands.get(commandName);
    }
}