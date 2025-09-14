package command;

/**
 * Интерфейс ICommand определяет методы для выполнения команд.
 */
public interface ICommand {
    void execute(String[] args, String username, String password);
    String getDescription();
}