package transport;

import java.io.Serializable;
import java.util.List;

public class Response implements Serializable {
    private List<String> messages;
    private boolean success;
    private int statusCode;

    public Response(List<String> messages) {
        this(messages, true, 200);
    }

    public Response(List<String> messages, boolean success, int statusCode) {
        this.messages = messages;
        this.success = success;
        this.statusCode = statusCode;
    }

    public List<String> getMessages() {
        return messages;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void printMessages() {
        for (String message : this.messages) {
            System.out.println(message);
        }
    }
}
