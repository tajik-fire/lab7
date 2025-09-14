package model;

import java.io.Serializable;
import java.util.Date;

/**
 * Класс LabWork представляет собой лабораторную работу с различными характеристиками.
 */
public class LabWork implements Comparable<LabWork>, Serializable {
    private long id; // Значение поля должно быть больше 0, уникальным и генерироваться автоматически
    private String name; // Поле не может быть null, строка не может быть пустой
    private Coordinates coordinates; // Поле не может быть null
    private Date creationDate; // Поле не может быть null, генерируется автоматически
    private float minimalPoint; // Значение поля должно быть больше 0
    private Long tunedInWorks; // Поле может быть null
    private Difficulty difficulty; // Поле не может быть null
    private Discipline discipline; // Поле может быть null
    private Integer ownerId; // Новое поле для хранения ID пользователя-владельца

    /**
     * Конструктор для создания нового объекта LabWork с автоматической генерацией id и creationDate.
     */
    public LabWork() {
        this.creationDate = new Date();
    }

    public LabWork(String name, Coordinates coordinates, float minimalPoint, Long tunedInWorks, Difficulty difficulty, Discipline discipline, Integer ownerId) {
        this.name = name;
        this.coordinates = coordinates;
        this.minimalPoint = minimalPoint;
        this.tunedInWorks = tunedInWorks;
        this.difficulty = difficulty;
        this.discipline = discipline;
        this.creationDate = new Date();
        this.ownerId = ownerId;
    }

    // Геттеры и сеттеры для всех полей
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Название лабораторной работы не может быть пустым");
        }
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        if (coordinates == null) {
            throw new IllegalArgumentException("Координаты не могут быть null");
        }
        this.coordinates = coordinates;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public float getMinimalPoint() {
        return minimalPoint;
    }

    public void setMinimalPoint(float minimalPoint) {
        if (minimalPoint <= 0) {
            throw new IllegalArgumentException("Минимальный балл должен быть больше 0");
        }
        this.minimalPoint = minimalPoint;
    }

    public Long getTunedInWorks() {
        return tunedInWorks;
    }

    public void setTunedInWorks(Long tunedInWorks) {
        this.tunedInWorks = tunedInWorks;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        if (difficulty == null) {
            throw new IllegalArgumentException("Уровень сложности не может быть null");
        }
        this.difficulty = difficulty;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public int compareTo(LabWork other) {
        return Long.compare(this.id, other.id);
    }

    @Override
    public String toString() {
        return "LabWork{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", minimalPoint=" + minimalPoint +
                ", tunedInWorks=" + tunedInWorks +
                ", difficulty=" + difficulty +
                ", discipline=" + discipline +
                ", ownerId=" + ownerId +
                '}';
    }
}