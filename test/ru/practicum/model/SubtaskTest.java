package ru.practicum.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.practicum.manager.Manager;
import ru.practicum.manager.TaskManager;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {
    private static Subtask subtask1;
    private static Subtask subtask2;
    private static TaskManager manager;

    @BeforeAll
    static void setUp() {
        subtask1 = new Subtask("Помыть машину", "Полная мойка с полировкой", 1, Status.NEW);
        subtask2 = new Subtask("Купить продукты", "Молоко, хлеб, яйца", 1, Status.NEW);
        manager = Manager.getDefault();
    }

    @Test
    void shouldBeEqualIfIdIsEqual() {
        assertEquals(subtask1, subtask2, "Задачи с одинаковым id должны быть равны");
    }

    @Test
    void shouldBeNotEqualIfIdIsDifferent() {
        Subtask subtask3 = new Subtask("Посадить дерево", "Найти поставщика саженцев", 1, Status.NEW); // epicId = 1
        subtask3.setId(2); // устанавливаем id = 2
        assertNotEquals(subtask1, subtask3, "Задачи с разными id не должны быть равны");
    }

    @Test
    void shouldNotAddSubtaskAsEpicToItself() {
        Epic epic = new Epic("Основной эпик", "Описание");
        manager.createEpic(epic);
        Subtask subtask = new Subtask("Подзадача", "Описание", epic.getId(), Status.NEW);
        manager.createSubtask(subtask);
        int originalEpicId = manager.getSubtask(subtask.getId()).getEpicId();
        Subtask updatedSubtask = new Subtask("Подзадача", "Описание", subtask.getId(), Status.NEW);
        updatedSubtask.setEpicId(subtask.getId()); // Пытаемся сделать подзадачу своим же эпиком
        manager.updateSubtask(updatedSubtask);
        assertEquals(originalEpicId, manager.getSubtask(subtask.getId()).getEpicId());
    }
}