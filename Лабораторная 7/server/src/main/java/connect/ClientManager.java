package connect;

import transport.Request;
import transport.Response;
import command.CommandProcessor;
import model.User;
import service.AuthService;

import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class ClientManager implements Runnable {
    private final ServerSocket serverSocket;
    private final CommandProcessor commandProcessor;
    private final AuthService authService;
    private final ExecutorService responseSendingThreadPool;
    private volatile boolean running = true;

    public ClientManager(int port, CommandProcessor commandProcessor, AuthService authService, ExecutorService responseSendingThreadPool) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.serverSocket.setSoTimeout(200); // короткий таймаут для accept()
        this.commandProcessor = commandProcessor;
        this.authService = authService;
        this.responseSendingThreadPool = responseSendingThreadPool;
    }

    @Override
    public void run() {
        System.out.println("ClientManager запущен на порту " + serverSocket.getLocalPort());
        while (running) {
            try {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> handleClient(clientSocket)).start(); 
            } catch (SocketTimeoutException e) {
            } catch (IOException e) {
                if (running) {
                    System.err.println("Ошибка при ожидании клиента: " + e.getMessage());
                }
            }
        }
    }

    private void handleClient(Socket clientSocket) {
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        try {
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());

            Object requestObj = in.readObject();
            if (requestObj instanceof Request request) {
                System.out.println("Получен запрос: " + request.getCommandName() + " от пользователя: " + request.getUsername());

                User authenticatedUser = null;
                String commandName = request.getCommandName();

                if ("register".equals(commandName)) {
                    try {
                        authenticatedUser = authService.register(request.getUsername(), request.getPassword());
                        System.out.println("Пользователь " + request.getUsername() + " успешно зарегистрирован.");
                        sendResponse(out, clientSocket, new Response(List.of("Регистрация прошла успешно!"), true, 200));
                    } catch (SQLException e) {
                        System.err.println("Ошибка SQL во время регистрации для " + request.getUsername() + ": " + e.getMessage());
                        sendResponse(out, clientSocket, new Response(List.of(e.getMessage()), false, 401));
                    }
                    return; 
                } else if ("login".equals(commandName)) {
                    try {
                        authenticatedUser = authService.authenticate(request.getUsername(), request.getPassword()).orElse(null);
                        if (authenticatedUser != null) {
                            System.out.println("Пользователь " + request.getUsername() + " успешно авторизован.");
                            sendResponse(out, clientSocket, new Response(List.of("Авторизация прошла успешно!"), true, 200));
                        } else {
                            System.out.println("Пользователь " + request.getUsername() + " не авторизован (неверный логин/пароль).");
                            sendResponse(out, clientSocket, new Response(List.of("Unauthorized: Invalid username or password"), false, 401));
                        }
                    } catch (SQLException e) {
                        System.err.println("Ошибка SQL во время аутентификации для " + request.getUsername() + ": " + e.getMessage());
                        sendResponse(out, clientSocket, new Response(List.of(e.getMessage()), false, 401));
                    }
                    return; 
                }
                try {
                    authenticatedUser = authService.authenticate(request.getUsername(), request.getPassword()).orElse(null);
                } catch (SQLException e) {
                    System.err.println("Ошибка SQL при повторной аутентификации для команды " + commandName + ": " + e.getMessage());
                    sendResponse(out, clientSocket, new Response(List.of("Error during authentication for command: " + e.getMessage()), false, 401));
                    return;
                }

                if (authenticatedUser == null) {
                    System.out.println("Несанкционированный запрос от " + request.getUsername() + ": " + commandName);
                    sendResponse(out, clientSocket, new Response(List.of("Unauthorized: Please login or register."), false, 401));
                    return;
                }

                User userForLambda = authenticatedUser;
                final ObjectOutputStream finalOut = out;
                final Socket finalClientSocket = clientSocket;
                new Thread(() -> processAndRespond(request, userForLambda, finalOut, finalClientSocket)).start(); 
            } else {
                System.err.println("Получен некорректный формат запроса: " + requestObj.getClass().getName());
                sendResponse(out, clientSocket, new Response(List.of("Error: Invalid request format"), false, 400));
            }

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Ошибка при обработке запроса клиента (чтение/запись): " + e.getMessage());
            try {
                if (in != null) in.close();
                if (out != null) out.close();
                if (clientSocket != null && !clientSocket.isClosed()) clientSocket.close();
            } catch (IOException closeEx) {
                System.err.println("Ошибка при закрытии потоков/сокета в handleClient после исключения: " + closeEx.getMessage());
            }
        }
    }

    private void processAndRespond(Request request, User user, ObjectOutputStream out, Socket clientSocket) {
        try {
            Response response = commandProcessor.processCommand(request, user);
            sendResponse(out, clientSocket, response);
            System.out.println("Ответ на запрос " + request.getCommandName() + " отправлен.");
        } catch (Exception e) {
            System.err.println("Ошибка при обработке команды " + request.getCommandName() + " от " + request.getUsername() + ": " + e.getMessage());
            try {
                sendResponse(out, clientSocket, new Response(List.of("Error processing command: " + e.getMessage()), false, 500));
            } catch (Exception sendEx) {
                System.err.println("Критическая ошибка: не удалось отправить сообщение об ошибке: " + sendEx.getMessage());
            }
        }
    }

    private void sendResponse(ObjectOutputStream out, Socket clientSocket, Response response) {
        responseSendingThreadPool.submit(() -> {
            try {
                out.writeObject(response);
                out.flush();
            } catch (IOException e) {
                System.err.println("Ошибка при отправке ответа клиенту: " + e.getMessage());
            } finally {
                try {
                    if (out != null) out.close();
                    if (clientSocket != null && !clientSocket.isClosed()) clientSocket.close();
                } catch (IOException e) {
                    System.err.println("Ошибка при закрытии потоков/сокета после отправки ответа: " + e.getMessage());
                }
            }
        });
    }

    public void shutdown() {
        this.running = false;
        try {
            serverSocket.close();
        } catch (IOException e) {
            System.err.println("Ошибка при закрытии ServerSocket: " + e.getMessage());
        }
    }
}
