package command;

import java.util.List;

import repository.ILabWorkRepository;
import transport.Request;
import model.User;

/**
 * Класс RemoveFirstCommand реализует команду remove_first для удаления первого элемента коллекции.
 */
public class RemoveFirstCommand implements ICommand {
    private ILabWorkRepository repository;

    public RemoveFirstCommand(ILabWorkRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<String> execute(Request request, User user) {
        try {
            if (!repository.getAll().isEmpty()) {
                // To remove the first element, we need to iterate and remove by id.
                // This is a simplification; a more efficient way would be to get the first element's ID and then remove by ID.
                // For now, we will simulate by removing the top of the stack and assuming it's the 'first' logically for this command.
                // In a real DB scenario, 'first' would need a definition (e.g., lowest ID, oldest creationDate).
                long idToRemove = repository.getAll().peek().getId();
                if (repository.removeById(idToRemove, user.getId())) {
                    return List.of("Первый элемент коллекции удален");
                } else {
                    return List.of("Не удалось удалить первый элемент (возможно, он вам не принадлежит или коллекция пуста).");
                }
            } else {
                return List.of("Коллекция пуста");
            }
        } catch (Exception e) {
            return List.of("Ошибка при удалении первого элемента: " + e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "удалить первый элемент из коллекции";
    }
}