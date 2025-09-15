package command;

import java.util.List;

import repository.ILabWorkRepository;
import transport.Request;
import model.User;

/**
 * Класс RemoveByIdCommand реализует команду remove_by_id для удаления элемента коллекции по id.
 */
public class RemoveByIdCommand implements ICommand {
    private ILabWorkRepository repository;

    public RemoveByIdCommand(ILabWorkRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<String> execute(Request request, User user) {
        long id = (Long) request.getNumber();
        try {
            if (repository.removeById(id, user.getId())) {
                return List.of("Элемент с id " + id + " удален");
            } else {
                return List.of("Элемент с id " + id + " не найден или не принадлежит вам.");
            }
        } catch (Exception e) {
            return List.of("Ошибка при удалении элемента: " + e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "удалить элемент из коллекции по его id";
    }
}