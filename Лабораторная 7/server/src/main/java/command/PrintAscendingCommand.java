package command;

import repository.ILabWorkRepository;
import transport.Request;
import model.LabWork;
import java.util.*;
import model.User;

public class PrintAscendingCommand implements ICommand {
    private ILabWorkRepository repository;

    public PrintAscendingCommand(ILabWorkRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<String> execute(Request request, User user) {
        try {
            Stack<LabWork> stack = repository.getAll();
            List<LabWork> list = new ArrayList<>(stack);
            List<String> answer = new ArrayList<>();
            list.sort(Comparator.naturalOrder());  // Сортировка по id (по умолчанию)
            
            for (LabWork labWork : list) {
                answer.add(labWork.toString());
            }

            return answer;
        } catch (Exception e) {
            return List.of("Ошибка при получении элементов: " + e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "вывести элементы коллекции в порядке возрастания";
    }
}