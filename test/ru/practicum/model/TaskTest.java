package ru.practicum.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    private static Task task1;
    private static Task task2;

    @BeforeAll
    static void setUp() {
        task1 = new Task(
                "Помыть машину",
                "Полная мойка с полировкой",
                1,
                Status.NEW,
                120,
                LocalDateTime.of(2025, 6, 1, 12, 30)
        );
        task2 = new Task(
                "Купить продукты",
                "Молоко, хлеб, яйца",
                1,
                Status.NEW,
                20,
                LocalDateTime.of(2025, 8, 12, 8, 30)
        );
    }

    @Test
    void shouldBeEqualIfIdIsEqual() {
        assertEquals(task1, task2, "Задачи с одинаковым id должны быть равны");
    }

    @Test
    void shouldBeNotEqualIfIdIsDifferent() {
        Task task3 = new Task(
                "Посадить дерево",
                "Найти поставщика саженцев",
                2,
                Status.NEW,
                20,
                LocalDateTime.of(2025, 9, 2, 12, 20)
        );
        assertNotEquals(task1, task3, "Задачи с разными id не должны быть равны");
    }
}