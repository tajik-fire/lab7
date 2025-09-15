package command;

import java.util.List;

import model.LabWork;
import repository.ILabWorkRepository;
import transport.Request;
import model.User;

/**
 * Класс AddCommand реализует команду add для добавления нового элемента в коллекцию.
 */
public class AddCommand implements ICommand {
    private ILabWorkRepository repository;

    public AddCommand(ILabWorkRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<String> execute(Request request, User user) {
        LabWork labWork = request.getLabWork();
        try {
            repository.add(labWork, user.getId());
            return List.of("Элемент добавлен в коллекцию");
        } catch (Exception e) {
            return List.of("Ошибка при добавлении элемента: " + e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "добавить новый элемент в коллекцию";
    }
}