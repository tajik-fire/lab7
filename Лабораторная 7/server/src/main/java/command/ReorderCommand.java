package command;

import repository.ILabWorkRepository;
import transport.Request;
import model.LabWork;
import java.util.*;
import model.User;

public class ReorderCommand implements ICommand {
    private ILabWorkRepository repository;

    public ReorderCommand(ILabWorkRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<String> execute(Request request, User user) {
        try {
            Stack<LabWork> stack = repository.getAll();
            List<LabWork> list = new ArrayList<>(stack);
            list.sort(Comparator.reverseOrder());  
            stack.clear();
            stack.addAll(list);
            return List.of("Коллекция отсортирована в обратном порядке");
        } catch (Exception e) {
            return List.of("Ошибка при переупорядочивании коллекции: " + e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "отсортировать коллекцию в порядке, обратном нынешнему";
    }
}