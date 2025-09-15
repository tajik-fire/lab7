import repository.LabWorkRepository;
import ui.ConsoleUI;
import command.CommandFactory;
import command.CommandProcessor;
import connect.ClientManager;
import database.DatabaseManager;
import repository.UserRepository;
import service.AuthService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Точка входа в серверное приложение.
 */
public class Server {
    private static final int PORT = 9738;
    private static final String DB_URL = "jdbc:postgresql://pg:5432/studs"; 
    private static final String DB_USER = "s465676"; 
    private static final String DB_PASSWORD = "M5d3JOepr27X5JQa";

    public static void main(String[] args) {
        DatabaseManager databaseManager = new DatabaseManager(DB_URL, DB_USER, DB_PASSWORD);
        try {
            databaseManager.initializeDatabase();
        } catch (Exception e) {
            System.err.println("Error initializing database: " + e.getMessage());
            return;
        }

        UserRepository userRepository = new UserRepository(databaseManager);
        AuthService authService = new AuthService(userRepository);

        LabWorkRepository labWorkRepository;
        try {
            labWorkRepository = new LabWorkRepository(databaseManager);
            System.out.println("Коллекция загружена из базы данных.");
        } catch (SQLException e) {
            System.err.println("Ошибка загрузки коллекции из базы данных: " + e.getMessage());
            return;
        }

        CommandFactory commandFactory = new CommandFactory(labWorkRepository, authService);
        CommandProcessor commandProcessor = new CommandProcessor(labWorkRepository, commandFactory);

        ExecutorService requestReadingThreadPool = Executors.newCachedThreadPool();
        ExecutorService responseSendingThreadPool = Executors.newFixedThreadPool(10); // Example fixed thread pool size

        try {
            ClientManager clientManager = new ClientManager(PORT, commandProcessor, authService, requestReadingThreadPool, responseSendingThreadPool);
            ConsoleUI consoleUI = new ConsoleUI(commandProcessor);

            Thread clientAccepterThread = new Thread(clientManager::run); 
            clientAccepterThread.start();

            System.out.println("Сервер запущен. Ожидание команд...");
            boolean running = true;
            while (running) {
                running = consoleUI.tryHandleCommand();
            }

            clientManager.shutdown();
            requestReadingThreadPool.shutdown();
            responseSendingThreadPool.shutdown();
            System.out.println("Сервер завершён.");

        } catch (IOException e) {
            System.err.println("Ошибка сервера: " + e.getMessage());
        }
    }
}
