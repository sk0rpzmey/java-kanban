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
    void shouldBePreservePreviousVersionTask() {
        Task originalTask = new Task("Оригинал", "Описание", Status.NEW);
        manager.createTask(originalTask);
        manager.getTask(originalTask.getId());
        originalTask.setTitle("Измененное название");
        originalTask.setStatus(Status.IN_PROGRESS);
        originalTask.setDescription("Новое описание");
        manager.updateTask(originalTask);

        List<Task> history = manager.getHistory();

        assertEquals(1, history.size(), "В истории должна быть одна задача");
        Task historicalTask = history.get(0);
        assertEquals("Оригинал", historicalTask.getTitle(),
                "В истории должно сохраниться оригинальное название");
        assertEquals("Описание", historicalTask.getDescription(),
                "В истории должно сохраниться оригинальное описание");
        assertEquals(Status.NEW, historicalTask.getStatus(),
                "В истории должен сохраниться оригинальный статус");
    }

    @Test
    void shouldBePreservePreviousVersionEpicAndSubtasks() {
        Epic epic = new Epic("Эпик", "Описание");
        manager.createEpic(epic);
        Subtask subtask = new Subtask("Подзадача", "Описание", epic.getId(), Status.NEW);
        manager.createSubtask(subtask);
        manager.getEpic(epic.getId());
        manager.getSubtask(subtask.getId());

        epic.setTitle("Новый эпик");
        subtask.setTitle("Новая подзадача");
        subtask.setStatus(Status.DONE);
        manager.updateEpic(epic);
        manager.updateSubtask(subtask);
        List<Task> history = manager.getHistory();

        assertEquals(2, history.size(), "В истории должно быть 2 задачи");
        Task historicalEpic = history.get(0);
        Task historicalSubtask = history.get(1);

        assertEquals("Эпик", historicalEpic.getTitle(),
                "В истории должен сохраниться оригинальный эпик");
        assertEquals("Подзадача", historicalSubtask.getTitle(),
                "В истории должен сохраниться оригинальная подзадача");
        assertEquals(Status.NEW, historicalSubtask.getStatus(),
                "В истории должен сохраниться оригинальный статус подзадачи");
    }

    @Test
    void shouldBeOnlyOneTskLeft() {
        Task taskWithId1 = new Task("Задача1", "Описание1", 1, Status.NEW);
        historyManager.addViewedTasks(taskWithId1);
        Task sameTaskWithId1 = new Task("Задача2", "Описание2", 1, Status.NEW);
        historyManager.addViewedTasks(sameTaskWithId1);
        assertEquals(1, historyManager.getHistory().size());
    }

    @Test
    void shouldBeTwoUniqueTsk() {
        Task taskWithId1 = new Task("Задача1", "Описание1", 1, Status.NEW);
        historyManager.addViewedTasks(taskWithId1);
        Task taskWithId2 = new Task("Задача2", "Описание2", 2, Status.NEW);
        historyManager.addViewedTasks(taskWithId2);
        Task sameTaskWithId1 = new Task("Задача1", "Описание1", 1, Status.NEW);
        historyManager.addViewedTasks(sameTaskWithId1);
        assertEquals(2, historyManager.getHistory().size());
    }
}