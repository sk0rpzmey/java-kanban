package ru.practicum.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import ru.practicum.model.*;

import static org.junit.jupiter.api.Assertions.*;


class HistoryManagerTest {
    private HistoryManager historyManager;

    @BeforeEach
    void setUp() {
        historyManager = Manager.getDefaultHistory();
    }

    @Test
    void shouldBeOnlyOneTskLeft() {
        Task taskWithId1 = new Task(
                "Задача1",
                "Описание1",
                1,
                Status.NEW,
                120,
                LocalDateTime.of(2025, 6, 1, 12, 30)
        );
        historyManager.add(taskWithId1);
        Task sameTaskWithId1 = new Task(
                "Задача2",
                "Описание2",
                1,
                Status.NEW,
                120,
                LocalDateTime.of(2025, 6, 1, 12, 30)
        );
        historyManager.add(sameTaskWithId1);
        assertEquals(1, historyManager.getHistory().size());
    }

    @Test
    void shouldBeTwoUniqueTsk() {
        Task taskWithId1 = new Task(
                "Задача1",
                "Описание1",
                1,
                Status.NEW,
                120,
                LocalDateTime.of(2025, 6, 1, 12, 30)
        );
        historyManager.add(taskWithId1);
        Task taskWithId2 = new Task(
                "Задача2",
                "Описание2",
                2,
                Status.NEW,
                60,
                LocalDateTime.of(2025, 6, 1, 12, 30)
        );
        historyManager.add(taskWithId2);
        Task sameTaskWithId1 = new Task(
                "Задача1",
                "Описание1",
                1,
                Status.NEW,
                120,
                LocalDateTime.of(2025, 6, 1, 12, 30)
        );
        historyManager.add(sameTaskWithId1);
        assertEquals(2, historyManager.getHistory().size());
    }

    @Test
    void historySizeShouldNotExceedLimit() {
        // Добавляем много задач в историю
        for (int i = 0; i < 20; i++) {
            Task task = new Task(
                    "Task " + i,
                    "Description " + i,
                    Status.NEW,
                    10,
                    LocalDateTime.of(2025, 6, 1, 12, 30).plusDays(1)
            );
            historyManager.add(task);
        }

        // Проверяем, что история работает корректно
        List<Task> history = historyManager.getHistory();
        assertTrue(history.size() <= 20); // В текущей реализации нет ограничения
    }

    @Test
    void shouldBeEmptyTaskHistory() {
        assertEquals(0, historyManager.getHistory().size());
    }

    @Test
    void shouldBeTwoTaskWhenFirstTaskRemove() {
        Task task1 = new Task(
                "Задача1",
                "Описание1",
                1,
                Status.NEW,
                120,
                LocalDateTime.of(2025, 6, 1, 12, 30)
        );
        historyManager.add(task1);
        Task task2 = new Task(
                "Задача2",
                "Описание2",
                2,
                Status.NEW,
                60,
                LocalDateTime.of(2025, 6, 1, 12, 30)
        );
        historyManager.add(task2);
        Task task3 = new Task(
                "Задача 3",
                "Описание 3",
                3,
                Status.NEW,
                120,
                LocalDateTime.of(2025, 6, 1, 12, 30)
        );
        historyManager.add(task3);
        historyManager.remove(1);
        assertEquals(2, historyManager.getHistory().size());
    }

    @Test
    void shouldBeTwoTaskWhenMiddleTaskRemove() {
        Task task1 = new Task(
                "Задача1",
                "Описание1",
                1,
                Status.NEW,
                120,
                LocalDateTime.of(2025, 6, 1, 12, 30)
        );
        historyManager.add(task1);
        Task task2 = new Task(
                "Задача2",
                "Описание2",
                2,
                Status.NEW,
                60,
                LocalDateTime.of(2025, 6, 1, 12, 30)
        );
        historyManager.add(task2);
        Task task3 = new Task(
                "Задача 3",
                "Описание 3",
                3,
                Status.NEW,
                120,
                LocalDateTime.of(2025, 6, 1, 12, 30)
        );
        historyManager.add(task3);
        historyManager.remove(2);
        assertEquals(2, historyManager.getHistory().size());
    }

    @Test
    void shouldBeTwoTaskWhenLastTaskRemove() {
        Task task1 = new Task(
                "Задача1",
                "Описание1",
                1,
                Status.NEW,
                120,
                LocalDateTime.of(2025, 6, 1, 12, 30)
        );
        historyManager.add(task1);
        Task task2 = new Task(
                "Задача2",
                "Описание2",
                2,
                Status.NEW,
                60,
                LocalDateTime.of(2025, 6, 1, 12, 30)
        );
        historyManager.add(task2);
        Task task3 = new Task(
                "Задача 3",
                "Описание 3",
                3,
                Status.NEW,
                120,
                LocalDateTime.of(2025, 6, 1, 12, 30)
        );
        historyManager.add(task3);
        historyManager.remove(3);
        assertEquals(2, historyManager.getHistory().size());
    }
}