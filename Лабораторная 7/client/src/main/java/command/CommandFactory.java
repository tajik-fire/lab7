package command;

import java.util.HashMap;
import java.util.Map;

import connect.ServerManager;

public class CommandFactory {
    private Map<String, ICommand> commands = new HashMap<>();
    private CommandProcessor commandProcessor; // Added for ExecuteScriptCommand

    public CommandFactory(ServerManager serverManager) {
        // Need to set commandProcessor after CommandFactory is instantiated for ExecuteScriptCommand
        // This is a bit of a circular dependency, will be handled during CommandProcessor init.
        // We'll pass null for now and set it later if needed or refactor.

        InputHelper inputHelper = new InputHelper();

        // Регистрация всех команд
        commands.put("help", new HelpCommand(commands));
        commands.put("info", new InfoCommand(serverManager));
        commands.put("show", new ShowCommand(serverManager));
        commands.put("add", new AddCommand(serverManager, inputHelper));
        commands.put("update", new UpdateCommand(serverManager, inputHelper));
        commands.put("remove_by_id", new RemoveByIdCommand(serverManager));
        commands.put("clear", new ClearCommand(serverManager));
        commands.put("execute_script", new ExecuteScriptCommand(null)); // CommandProcessor will be set later
        commands.put("remove_first", new RemoveFirstCommand(serverManager));
        commands.put("reorder", new ReorderCommand(serverManager));
        commands.put("sort", new SortCommand(serverManager));
        commands.put("filter_greater_than_discipline", new FilterGreaterThanDisciplineCommand(serverManager, inputHelper));
        commands.put("print_ascending", new PrintAscendingCommand(serverManager));
        commands.put("print_field_descending_minimal_point", new PrintFieldDescendingMinimalPointCommand(serverManager));
        // commands.put("login", new LoginCommand(serverManager)); // Удалено, логика перенесена в ConsoleUI
        // commands.put("register", new RegisterCommand(serverManager)); // Удалено, логика перенесена в ConsoleUI
    }

    // This method can be used by CommandProcessor to set itself after CommandFactory is created
    public void setCommandProcessor(CommandProcessor commandProcessor) {
        this.commandProcessor = commandProcessor;
        ((ExecuteScriptCommand) commands.get("execute_script")).setCommandProcessor(commandProcessor);
    }

    public ICommand createCommand(String commandName) {
        return commands.get(commandName);
    }
}