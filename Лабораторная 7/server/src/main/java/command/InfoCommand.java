package command;

import repository.ILabWorkRepository;
import transport.Request;

import java.util.Date;
import java.util.List;
import model.User;

/**
 * Класс InfoCommand реализует команду info для вывода информации о коллекции.
 */
public class InfoCommand implements ICommand {
    private ILabWorkRepository repository;

    public InfoCommand(ILabWorkRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<String> execute(Request request, User user) {
        try {
            return List.of("Тип коллекции: " + repository.getClass().getName(), "Дата инициализации: " + new Date(), "Количество элементов: " + repository.getAll().size());
        } catch (Exception e) {
            return List.of("Ошибка при получении информации о коллекции: " + e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "вывести информацию о коллекции";
    }
}