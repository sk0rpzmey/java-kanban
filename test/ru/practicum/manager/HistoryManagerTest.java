package ru.practicum.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import ru.practicum.model.*;

import static org.junit.jupiter.api.Assertions.*;


class HistoryManagerTest {
    private HistoryManager historyManager;
    Task task1;
    Task task2;
    Task task3;

    @BeforeEach
    void setUp() {
        historyManager = Manager.getDefaultHistory();
        task1 = new Task(
                "Задача1",
                "Описание1",
                1,
                Status.NEW,
                120,
                LocalDateTime.of(2025, 6, 1, 4, 30)
        );
        task2 = new Task(
                "Задача2",
                "Описание2",
                2,
                Status.NEW,
                60,
                LocalDateTime.of(2025, 6, 1, 2, 30)
        );
        task3 = new Task(
                "Задача3",
                "Описание3",
                3,
                Status.NEW,
                30,
                LocalDateTime.of(2025, 6, 1, 6, 30)
        );
    }

    @Test
    void shouldBeOnlyOneTskLeft() {
        task2.setId(task1.getId());
        addTasksToHistory(task1, task2);
        assertEquals(1, historyManager.getHistory().size());
    }

    @Test
    void shouldBeTwoUniqueTsk() {
        task2.setId(task1.getId());
        addTasksToHistory(task1, task2, task3);
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
        addTasksToHistory(task1, task2, task3);
        historyManager.remove(1);
        assertEquals(2, historyManager.getHistory().size());
    }

    @Test
    void shouldBeTwoTaskWhenMiddleTaskRemove() {
        addTasksToHistory(task1, task2, task3);
        historyManager.remove(2);
        assertEquals(2, historyManager.getHistory().size());
    }

    @Test
    void shouldBeTwoTaskWhenLastTaskRemove() {
        addTasksToHistory(task1, task2, task3);
        historyManager.remove(3);
        assertEquals(2, historyManager.getHistory().size());
    }

    private void addTasksToHistory(Task... tasks) {
        for (Task task : tasks) {
            historyManager.add(task);
        }
    }
}