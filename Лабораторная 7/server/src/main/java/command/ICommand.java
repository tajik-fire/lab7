package command;

import model.User;
import java.util.List;

import transport.Request;

/**
 * Интерфейс ICommand определяет методы для выполнения команд.
 */
public interface ICommand {
    List<String> execute(Request request, User user);
    String getDescription();
}