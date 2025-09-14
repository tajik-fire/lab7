package model;

import java.io.Serializable;

/**
 * Класс Coordinates представляет собой координаты с x и y.
 */
public class Coordinates implements Serializable {
    private long x;
    private Integer y; // Максимальное значение поля: 710, поле не может быть null

    public Coordinates() {
        // Default constructor for ORM or manual instantiation
    }

    /**
     * Конструктор для создания объекта Coordinates.
     *
     * @param x значение x координаты
     * @param y значение y координаты
     */
    public Coordinates(long x, Integer y) {
        this.x = x;
        if (y != null && y > 710) {
            throw new IllegalArgumentException("Значение y не может превышать 710");
        }
        this.y = y;
    }

    // Геттеры и сеттеры
    public long getX() {
        return x;
    }

    public void setX(long x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        if (y != null && y > 710) {
            throw new IllegalArgumentException("Значение y не может превышать 710");
        }
        this.y = y;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}