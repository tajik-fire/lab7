package command;

import model.Coordinates;
import model.Discipline;
import model.Difficulty;
import model.LabWork;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Класс InputHelper помогает вводу данных пользователем для создания или обновления объектов LabWork.
 */
public class InputHelper {
    private Scanner scanner = new Scanner(System.in);

    public LabWork createLabWork() throws Exception {
        LabWork labWork = new LabWork();

        System.out.print("Введите название лабораторной работы: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Название не может быть пустым");
        }
        labWork.setName(name);

        System.out.print("Введите x координаты: ");
        long x = Long.parseLong(scanner.nextLine().trim());
        System.out.print("Введите y координаты (максимум 710): ");
        String yStr = scanner.nextLine().trim();
        Integer y = null;
        if (!yStr.isEmpty()) {
            y = Integer.parseInt(yStr);
            if (y > 710) {
                throw new IllegalArgumentException("Значение y не может превышать 710");
            }
        }
        labWork.setCoordinates(new Coordinates(x, y));

        System.out.print("Введите минимальный балл: ");
        float minimalPoint = Float.parseFloat(scanner.nextLine().trim());
        if (minimalPoint <= 0) {
            throw new IllegalArgumentException("Минимальный балл должен быть больше 0");
        }
        labWork.setMinimalPoint(minimalPoint);

        System.out.print("Введите количество выполненных работ (оставьте пустым для null): ");
        String tunedInWorksStr = scanner.nextLine().trim();
        Long tunedInWorks = null;
        if (!tunedInWorksStr.isEmpty()) {
            tunedInWorks = Long.parseLong(tunedInWorksStr);
        }
        labWork.setTunedInWorks(tunedInWorks);

        System.out.println("Доступные уровни сложности: " + Arrays.toString(Difficulty.values()));
        System.out.print("Введите уровень сложности: ");
        String difficultyStr = scanner.nextLine().trim();
        Difficulty difficulty = Difficulty.valueOf(difficultyStr.toUpperCase());
        labWork.setDifficulty(difficulty);

        System.out.print("Введите название дисциплины (оставьте пустым для null): ");
        String disciplineName = scanner.nextLine().trim();
        Discipline discipline = null;
        if (!disciplineName.isEmpty()) {
            System.out.print("Введите количество часов лекций: ");
            int lectureHours = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Введите количество часов самоподготовки: ");
            int selfStudyHours = Integer.parseInt(scanner.nextLine().trim());
            discipline = new Discipline(disciplineName, lectureHours, selfStudyHours);
        }
        labWork.setDiscipline(discipline);

        return labWork;
    }

    public Discipline readDiscipline() {
        System.out.print("Введите название дисциплины: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Название дисциплины не может быть пустым");
        }

        System.out.print("Введите количество часов лекций: ");
        int lectureHours = scanner.nextInt();
        scanner.nextLine(); // Считываем остаток строки

        System.out.print("Введите количество часов самоподготовки: ");
        int selfStudyHours = scanner.nextInt();
        scanner.nextLine(); // Считываем остаток строки

        return new Discipline(name, lectureHours, selfStudyHours);
    }

}