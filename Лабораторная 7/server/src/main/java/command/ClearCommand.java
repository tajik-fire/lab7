package command;

import java.util.List;

import repository.ILabWorkRepository;
import transport.Request;
import model.User;

/**
 * Класс ClearCommand реализует команду clear для очистки коллекции.
 */
public class ClearCommand implements ICommand {
    private ILabWorkRepository repository;

    public ClearCommand(ILabWorkRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<String> execute(Request request, User user) {
        try {
            repository.clear(user.getId());
            return List.of("Ваши элементы коллекции очищены");
        } catch (Exception e) {
            return List.of("Ошибка при очистке коллекции: " + e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "очистить коллекцию";
    }
}