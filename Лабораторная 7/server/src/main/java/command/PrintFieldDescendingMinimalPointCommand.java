package command;

import repository.ILabWorkRepository;
import transport.Request;
import model.LabWork;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import model.User;

public class PrintFieldDescendingMinimalPointCommand implements ICommand {
    private ILabWorkRepository repository;

    public PrintFieldDescendingMinimalPointCommand(ILabWorkRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<String> execute(Request request, User user) {
        try {
            List<Float> points = repository.getAll().stream()
                    .map(LabWork::getMinimalPoint)
                    .sorted(Comparator.reverseOrder())
                    .toList();
            List<String> answer = new ArrayList<>();

            for (Float point : points) {
                answer.add(point.toString());
            }

            return answer;
        } catch (Exception e) {
            return List.of("Ошибка при получении минимальных баллов: " + e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "вывести значения поля minimalPoint всех элементов в порядке убывания";
    }
}