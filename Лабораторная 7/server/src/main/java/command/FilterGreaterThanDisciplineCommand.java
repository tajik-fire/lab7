package command;

import model.Discipline;
import model.LabWork;
import repository.ILabWorkRepository;
import transport.Request;

import java.util.ArrayList;
import java.util.List;
import model.User;

public class FilterGreaterThanDisciplineCommand implements ICommand {
    private ILabWorkRepository repository;

    public FilterGreaterThanDisciplineCommand(ILabWorkRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<String> execute(Request request, User user) {
        Discipline discipline = request.getDiscipline();
        List<String> answer = new ArrayList<>();
        
        try {
            for (LabWork labWork : repository.getAll()) {
                if (labWork.getDiscipline() != null && labWork.getDiscipline().compareTo(discipline) > 0) {
                    answer.add(labWork.toString());
                }
            }
            return answer;
        } catch (Exception e) {
            return List.of("Ошибка при фильтрации элементов: " + e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "вывести элементы, значение поля discipline которых больше заданного";
    }
}