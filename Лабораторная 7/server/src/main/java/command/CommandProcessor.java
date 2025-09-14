package command;

import model.User;
import repository.ILabWorkRepository;
import transport.Request;
import transport.Response;

import java.util.List;

public class CommandProcessor {
    private CommandFactory commandFactory;

    public CommandProcessor(ILabWorkRepository repository, CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    public Response processCommand(Request request, User user) {
        String commandName = request.getCommandName();
        ICommand command = commandFactory.createCommand(commandName);

        if (command == null) {
            return new Response(List.of("Неизвестная команда: " + commandName), false, 400);
        }

        // Special handling for 'register' command, as it doesn't require prior authentication
        if (commandName.equals("register")) {
            // The registration is handled in ClientManager, so this should not be called.
            // If it is, it's an internal error or misuse.
            return new Response(List.of("Error: Registration should be handled before command processing."), false, 500);
        }

        // All other commands require an authenticated user
        if (user == null) {
            return new Response(List.of("Unauthorized: Please login or register."), false, 401);
        }

        try {
            return new Response(command.execute(request, user));
        } catch (Exception e) {
            return new Response(List.of("Ошибка при выполнении команды: " + e.getMessage()), false, 500);
        }
    }
}