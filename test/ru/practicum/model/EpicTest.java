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
        epic2 = new Epic("Купить продукты", "Молоко, хлеб, яйца", 1);
        manager = Manager.getDefault();
    }

    @Test
    void shouldBeEqualIfIdIsEqual() {
        assertEquals(epic1, epic2, "Задачи с одинаковым id должны быть равны");
    }

    @Test
    void shouldBeNotEqualIfIdIsDifferent() {
        Epic epic3 = new Epic("Посадить дерево", "Найти поставщика саженцев", 2);
        assertNotEquals(epic1, epic3, "Задачи с разными id не должны быть равны");
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
                LocalDateTime.of(2025, 8, 12, 8, 30)
        );
        manager.createSubtask(subtask);
        Subtask updatedSubtask = new Subtask(
                "Подзадача",
                "Описание",
                subtask.getId(),
                Status.NEW,
                720,
                LocalDateTime.of(2025, 8, 12, 8, 30)
        );
        updatedSubtask.setEpicId(updatedSubtask.getId());
        manager.updateSubtask(updatedSubtask);
        assertEquals(epic.getId(), manager.getSubtask(subtask.getId()).getEpicId());
    }
}