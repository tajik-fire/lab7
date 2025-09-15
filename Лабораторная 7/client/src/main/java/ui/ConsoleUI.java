package ui;

import command.CommandProcessor;
import connect.ServerManager;
import transport.Response;

import java.util.Scanner;

public class ConsoleUI {
    private CommandProcessor commandProcessor;
    private String username;
    private String password;
    private ServerManager serverManager; 

    public ConsoleUI(CommandProcessor commandProcessor, ServerManager serverManager) {
        this.commandProcessor = commandProcessor;
        this.serverManager = serverManager; 
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Добро пожаловать в программу управления коллекцией LabWork!");

        while (true) {
            System.out.println("Выберите действие:");
            System.out.println("1. Зарегистрироваться");
            System.out.println("2. Войти");
            System.out.print("Ваш выбор: ");

            String choice = scanner.nextLine().trim();
            String actionCommand;

            if ("1".equals(choice)) {
                actionCommand = "register";
            } else if ("2".equals(choice)) {
                actionCommand = "login";
            } else {
                System.out.println("Некорректный выбор. Пожалуйста, введите '1' или '2'.");
                continue;
            }

            System.out.print("Введите ваш логин: ");
            username = scanner.nextLine().trim();

            System.out.print("Введите ваш пароль: ");
            password = scanner.nextLine().trim();

            try {
                Response authResponse = serverManager.sendCommand(actionCommand, null, null, null, username, password);

                if (authResponse != null && authResponse.isSuccess()) {
                    System.out.println("Добро пожаловать, " + username + "!");
                    break; 
                } else {
                    String errorMessage = (authResponse != null && !authResponse.getMessages().isEmpty()) 
                                        ? authResponse.getMessages().get(0) : "Неизвестная ошибка";
                    System.out.println("Ошибка авторизации/регистрации: " + errorMessage);
                }
            } catch (Exception e) {
                System.err.println("Ошибка связи с сервером во время аутентификации: " + e.getMessage());
            }
        }

        System.out.println("Введите 'help' для получения списка доступных команд");

        while (true) {
            System.out.print("> ");
            String commandLine = scanner.nextLine().trim();
            if (commandLine.isEmpty()) continue;

            try {
                commandProcessor.processCommand(commandLine, username, password);
            } catch (Exception e) {
                System.err.println("Ошибка выполнения команды: " + e.getMessage());
            }
        }
    }
}