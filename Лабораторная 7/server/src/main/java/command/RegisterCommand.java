package command;

import model.User;
import service.AuthService;
import transport.Request;
import java.util.List;

public class RegisterCommand implements ICommand {
    private final AuthService authService;

    public RegisterCommand(AuthService authService) {
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
            authService.register(username, password);
            return List.of("Пользователь " + username + " успешно зарегистрирован.");
        } catch (Exception e) {
            return List.of("Ошибка при регистрации: " + e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "зарегистрировать нового пользователя (логин, пароль)";
    }
}
