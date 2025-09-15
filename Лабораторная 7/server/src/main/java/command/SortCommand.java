package command;

import repository.ILabWorkRepository;
import transport.Request;
import model.LabWork;
import java.util.List;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Comparator;
import model.User;

public class SortCommand implements ICommand {
    private ILabWorkRepository repository;

    public SortCommand(ILabWorkRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<String> execute(Request request, User user) {
        try {
            Stack<LabWork> stack = repository.getAll();
            List<LabWork> list = new ArrayList<>(stack);
            list.sort(Comparator.naturalOrder());  // Сортировка по id (по умолчанию)
            stack.clear();
            stack.addAll(list);
            return List.of("Коллекция отсортирована в естественном порядке");
        } catch (Exception e) {
            return List.of("Ошибка при сортировке коллекции: " + e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "отсортировать коллекцию в естественном порядке";
    }
}