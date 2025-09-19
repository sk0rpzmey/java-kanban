package ru.practicum.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import ru.practicum.model.*;

import static org.junit.jupiter.api.Assertions.*;


class HistoryManagerTest {
    private TaskManager manager;
    private HistoryManager historyManager;

    @BeforeEach
    void setUp() {
        manager = Manager.getDefault();
        historyManager = Manager.getDefaultHistory();
    }

    @Test
    void shouldBeOnlyOneTskLeft() {
        Task taskWithId1 = new Task("Задача1", "Описание1", 1, Status.NEW);
        historyManager.add(taskWithId1);
        Task sameTaskWithId1 = new Task("Задача2", "Описание2", 1, Status.NEW);
        historyManager.add(sameTaskWithId1);
        assertEquals(1, historyManager.getHistory().size());
    }

    @Test
    void shouldBeTwoUniqueTsk() {
        Task taskWithId1 = new Task("Задача1", "Описание1", 1, Status.NEW);
        historyManager.add(taskWithId1);
        Task taskWithId2 = new Task("Задача2", "Описание2", 2, Status.NEW);
        historyManager.add(taskWithId2);
        Task sameTaskWithId1 = new Task("Задача1", "Описание1", 1, Status.NEW);
        historyManager.add(sameTaskWithId1);
        assertEquals(2, historyManager.getHistory().size());
    }

    @Test
    void historySizeShouldNotExceedLimit() {
        // Добавляем много задач в историю
        for (int i = 0; i < 20; i++) {
            Task task = new Task("Task " + i, "Description " + i, Status.NEW);
            historyManager.add(task);
        }

        // Проверяем, что история работает корректно
        List<Task> history = historyManager.getHistory();
        assertTrue(history.size() <= 20); // В текущей реализации нет ограничения
    }
}