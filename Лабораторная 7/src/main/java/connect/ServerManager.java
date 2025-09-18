package connect;

import java.io.*;
import java.net.*;
import java.util.List;

import model.Discipline;
import model.LabWork;
import transport.Request;
import transport.Response;

public class ServerManager {
    private final String host;
    private final int port;

    public ServerManager(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Response sendCommand(final String commandName, LabWork labWork, Discipline discipline, Number number, String username, String password) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(this.host, this.port), 10000); // timeout 10 сек
            socket.setSoTimeout(10000); // ожидание ответа

            // Отправка запроса
            Request request = new Request(commandName, labWork, discipline, number, username, password);
            try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                out.writeObject(request);
                out.flush();

                // Получение ответа
                Object responseObj = in.readObject();
                if (responseObj instanceof Response) {
                    return (Response) responseObj;
                } else {
                    return new Response(List.of("Error: Invalid response from server"));
                }
            }

        } catch (SocketTimeoutException e) {
            return new Response(List.of("Error: Timeout waiting for server response", "Error: Server is temporarily unavailable"));
        } catch (IOException | ClassNotFoundException e) {
            return new Response(List.of("Error: Failed to communicate with server"));
        }
    }
}
