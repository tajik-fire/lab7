package command;

import model.User;
import service.AuthService;
import transport.Request;
import java.util.List;

public class LoginCommand implements ICommand {
    private final AuthService authService;

    public LoginCommand(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public List<String> execute(Request request, User user) {
        if (user != null) {
            return List.of("Вы уже авторизованы.");
        }

        String username = request.getUsername();
        String password = request.getPassword();

        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            return List.of("Логин и пароль не могут быть пустыми.");
        }

        try {
            // Authentication logic is handled in ClientManager, this command is mostly for consistency
            // and to provide a command description.
            authService.authenticate(username, password).orElseThrow(() -> new Exception("Invalid username or password."));
            return List.of("Пользователь " + username + " успешно авторизован.");
        } catch (Exception e) {
            return List.of("Ошибка при авторизации: " + e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "авторизоваться (логин, пароль)";
    }
}
