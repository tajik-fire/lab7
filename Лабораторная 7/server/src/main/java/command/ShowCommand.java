package command;

import java.util.ArrayList;
import java.util.List;

import model.LabWork;
import repository.ILabWorkRepository;
import transport.Request;
import model.User;

/**
 * Класс ShowCommand реализует команду show для вывода всех элементов коллекции.
 */
public class ShowCommand implements ICommand {
    private ILabWorkRepository repository;

    public ShowCommand(ILabWorkRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<String> execute(Request request, User user) {
        List<String> answer = new ArrayList<>();
        try {
            for (LabWork labWork : repository.getAll()) {
                answer.add(labWork.toString());
            }
        } catch (Exception e) {
            return List.of("Ошибка при отображении элементов: " + e.getMessage());
        }
        return answer;
    }

    @Override
    public String getDescription() {
        return "вывести все элементы коллекции";
    }
}