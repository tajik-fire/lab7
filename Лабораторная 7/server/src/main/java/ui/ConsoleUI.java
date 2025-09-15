package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import command.CommandProcessor;

public class ConsoleUI {
    private final BufferedReader console;
    private final CommandProcessor commandProcessor;

    public ConsoleUI(CommandProcessor commandProcessor) {
        this.commandProcessor = commandProcessor;
        this.console = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * Обрабатывает одну команду с консоли, если она доступна.
     * @return true — если сервер должен продолжать работать
     */
    public boolean tryHandleCommand() throws IOException {
        if (console.ready()) {
            String commandLine = console.readLine();

            if (commandLine.equals("exit")) {
                return false;
            } else {
                System.out.println("Неизвестная команда для сервера: " + commandLine);
            }
        }
        return true;
    }
}
