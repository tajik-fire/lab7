package model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Класс Discipline представляет собой дисциплину с названием и часами.
 */
public class Discipline implements Comparable<Discipline>, Serializable {
    private String name; // Поле не может быть null, строка не может быть пустой
    private int lectureHours;
    private int selfStudyHours;

    public Discipline(String name, int lectureHours, int selfStudyHours) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Название дисциплины не может быть пустым");
        }
        this.name = name;
        this.lectureHours = lectureHours;
        this.selfStudyHours = selfStudyHours;
    }

    public Discipline(String name, int labsCount) {
        this(name, 0, labsCount); // Assuming labsCount maps to selfStudyHours if lectureHours is 0
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Название дисциплины не может быть пустым");
        }
        this.name = name;
    }

    public int getLectureHours() {
        return lectureHours;
    }

    public void setLectureHours(int lectureHours) {
        this.lectureHours = lectureHours;
    }

    public int getSelfStudyHours() {
        return selfStudyHours;
    }

    public void setSelfStudyHours(int selfStudyHours) {
        this.selfStudyHours = selfStudyHours;
    }

    @Override
    public int compareTo(Discipline other) {
        // Сравнение по имени дисциплины
        return this.name.compareTo(other.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discipline that = (Discipline) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Discipline{" +
                "name='" + name + '\'' +
                ", lectureHours=" + lectureHours +
                ", selfStudyHours=" + selfStudyHours +
                '}';
    }

    public int getLabsCount() {
        return selfStudyHours; // Assuming labsCount maps to selfStudyHours
    }
}