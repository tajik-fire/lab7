package command;

import java.util.List;

import model.LabWork;
import repository.ILabWorkRepository;
import transport.Request;
import model.User;

/**
 * Класс UpdateCommand реализует команду update для обновления элемента коллекции по id.
 */
public class UpdateCommand implements ICommand {
    private ILabWorkRepository repository;

    public UpdateCommand(ILabWorkRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<String> execute(Request request, User user) {
        long id = (Long) request.getNumber();
        LabWork labWork = request.getLabWork();
        try {
            repository.update(id, labWork, user.getId());
            return List.of("Элемент с id " + id + " обновлен");
        } catch (Exception e) {
            return List.of("Ошибка при обновлении элемента: " + e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "обновить значение элемента коллекции по его id";
    }
}