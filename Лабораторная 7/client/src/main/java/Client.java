import command.CommandFactory;
import command.CommandProcessor;
import connect.ServerManager;
import ui.ConsoleUI;

public class Client {
    public static void main(String[] args) {    
        ServerManager serverManager = new ServerManager("localhost", 9738);
        CommandFactory commandFactory = new CommandFactory(serverManager);
        CommandProcessor commandProcessor = new CommandProcessor(commandFactory, serverManager);
        ConsoleUI consoleUI = new ConsoleUI(commandProcessor, serverManager);
        
        consoleUI.start();
    }
}
