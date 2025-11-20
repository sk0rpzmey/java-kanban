package ru.practicum.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.practicum.manager.Manager;
import ru.practicum.manager.TaskManager;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    private static Epic epic1;
    private static Epic epic2;
    private static TaskManager manager;

    @BeforeAll
    static void setUp() {
        epic1 = new Epic("Помыть машину", "Полная мойка с полировкой", 1);
        epic2 = new Epic("Купить продукты", "Молоко, хлеб, яйца", 2);
        manager = Manager.getDefault();
    }

    @Test
    void shouldBeEqualIfIdIsEqual() {
        Epic epic3 = new Epic("Посадить дерево", "Найти поставщика саженцев", 2);
        assertEquals(epic3, epic2, "Задачи с одинаковым id должны быть равны");
    }

    @Test
    void shouldBeNotEqualIfIdIsDifferent() {
        assertNotEquals(epic1, epic2, "Задачи с разными id не должны быть равны");
    }

    @Test
    void shouldNotAddEpicAsSubtaskToItself() {
        Epic epic = new Epic("Ремонт", "Описание");
        manager.createEpic(epic);
        Subtask subtask = new Subtask(
                "Подзадача",
                "Описание",
                epic.getId(),
                Status.NEW,
                720,
                LocalDateTime.of(2025, 8, 1, 8, 30)
        );
        manager.createSubtask(subtask);
        Subtask updatedSubtask = new Subtask(
                "Подзадача",
                "Описание",
                subtask.getId(),
                Status.NEW,
                720,
                LocalDateTime.of(2025, 8, 2, 8, 30)
        );
        updatedSubtask.setEpicId(updatedSubtask.getId());
        manager.updateSubtask(updatedSubtask);
        assertEquals(epic.getId(), manager.getSubtask(subtask.getId()).getEpicId());
    }

    @Test
    void shouldSetEpicToNewStatusIfAllSubtasksIsNew() {
        manager.createEpic(epic1);
        Subtask subtask1 = new Subtask(
                "Демонтаж стен",
                "",
                epic1.getId(),
                Status.NEW,
                720,
                LocalDateTime.of(2025, 8, 12, 8, 30)
        );
        Subtask subtask2 = new Subtask(
                "Укладка плитки",
                "",
                epic1.getId(),
                Status.NEW,
                220,
                LocalDateTime.of(2025, 9, 12, 8, 30));
        manager.createSubtask(subtask1);
        manager.createSubtask(subtask2);
        assertEquals(Status.NEW, epic1.getStatus());
    }

    @Test
    void shouldSetEpicToDoneStatusIfAllSubtasksIsDone() {
        manager.createEpic(epic2);
        Subtask subtask1 = new Subtask(
                "Демонтаж стен",
                "",
                epic2.getId(),
                Status.DONE,
                720,
                LocalDateTime.of(2025, 7, 12, 8, 30)
        );
        Subtask subtask2 = new Subtask(
                "Укладка плитки",
                "",
                epic2.getId(),
                Status.DONE,
                220,
                LocalDateTime.of(2025, 6, 12, 8, 30));
        manager.createSubtask(subtask1);
        manager.createSubtask(subtask2);
        assertEquals(Status.DONE, epic2.getStatus());
    }

    @Test
    void shouldSetEpicToInProgressStatusIfNotAllSubtasksAreDoneOrNew() {
        Epic epic3 = new Epic("Посадить дерево", "Найти поставщика саженцев", 3);
        manager.createEpic(epic3);
        Subtask subtask1 = new Subtask(
                "Демонтаж стен",
                "",
                epic3.getId(),
                Status.NEW,
                720,
                LocalDateTime.of(2025, 7, 15, 8, 30)
        );
        Subtask subtask2 = new Subtask(
                "Укладка плитки",
                "",
                epic3.getId(),
                Status.DONE,
                220,
                LocalDateTime.of(2025, 6, 8, 8, 30));
        manager.createSubtask(subtask1);
        manager.createSubtask(subtask2);
        assertEquals(Status.IN_PROGRESS, epic3.getStatus());
    }

    @Test
    void shouldSetEpicToInProgressStatusIfAllSubtasksIsInProgress() {
        Epic epic4 = new Epic("Посадить дерево", "Найти поставщика саженцев", 4);
        manager.createEpic(epic4);
        Subtask subtask1 = new Subtask(
                "Демонтаж стен",
                "",
                epic4.getId(),
                Status.IN_PROGRESS,
                720,
                LocalDateTime.of(2025, 7, 5, 8, 30)
        );
        Subtask subtask2 = new Subtask(
                "Укладка плитки",
                "",
                epic4.getId(),
                Status.IN_PROGRESS,
                220,
                LocalDateTime.of(2025, 6, 6, 8, 30));
        manager.createSubtask(subtask1);
        manager.createSubtask(subtask2);
        assertEquals(Status.IN_PROGRESS, epic4.getStatus());
    }
}