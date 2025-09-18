package command;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ExecuteScriptCommand implements ICommand {
    private CommandProcessor commandProcessor;

    public ExecuteScriptCommand(CommandProcessor commandProcessor) {
        this.commandProcessor = commandProcessor;
    }

    public void setCommandProcessor(CommandProcessor commandProcessor) {
        this.commandProcessor = commandProcessor;
    }

    @Override
    public void execute(String[] args, String username, String password) {
        if (args.length < 1) {
            System.out.println("Не указано имя файла для выполнения скрипта");
            return;
        }
        String fileName = args[0];
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("execute_script")) {
                    System.out.println("Использована запрещенная команда в файле: " + line);
                    return;
                 }
                commandProcessor.processCommand(line, username, password);
            }
            System.out.println("Команды из файла " + fileName + " выполнены");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "считать и исполнить скрипт из указанного файла";
    }
}
