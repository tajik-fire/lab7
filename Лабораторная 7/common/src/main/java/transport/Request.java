package transport;

import java.io.Serializable;

import model.Discipline;
import model.LabWork;

public class Request implements Serializable {
    private String commandName;
    private LabWork labWork;
    private Discipline discipline;
    private Number number;
    private String username;
    private String password;


    public Request(String commandName, LabWork labWork, Discipline discipline, Number number, String username, String password) {
        this.commandName = commandName;
        this.labWork = labWork;
        this.discipline = discipline;
        this.number = number;
        this.username = username;
        this.password = password;

    }

    public String getCommandName() {
        return this.commandName;
    }

    public LabWork getLabWork() {
        return this.labWork;
    }

    public Discipline getDiscipline() {
        return this.discipline;
    }

    public Number getNumber() {
        return this.number;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
