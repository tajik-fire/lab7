package model;

import java.io.Serializable;

/**
 * Перечисление Difficulty представляет собой уровни сложности лабораторной работы.
 */
public enum Difficulty implements Serializable {
    VERY_EASY,
    NORMAL,
    HARD,
    VERY_HARD,
    INSANE;
}