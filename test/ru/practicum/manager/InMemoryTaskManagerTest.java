package ru.practicum.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.model.*;


import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    private TaskManager manager;

    @BeforeEach
    void setUp() {
        manager = Manager.getDefault();
    }

    @Test
    void shouldAddAndFindDifferentTaskTypes() {
        Task task = new Task(
                "Помыть машину",
                "Полная мойка с полировкой",
                Status.NEW,
                20,
                LocalDateTime.of(2025, 8, 12, 8, 30)
        );
        manager.createTask(task);
        Epic epic = new Epic("Ремонт квартиры", "Полный цикл работ");
        manager.createEpic(epic);
        Subtask subtask = new Subtask(
                "Демонтаж стен",
                "",
                2,
                Status.NEW,
                720,
                LocalDateTime.of(2025, 8, 12, 8, 30)
        );
        manager.createSubtask(subtask);
        Task foundTask = manager.getTask(task.getId());
        Epic foundEpic = manager.getEpic(epic.getId());
        Subtask foundSubtask = manager.getSubtask(subtask.getId());


        assertNotNull(foundTask, "Задача должна находиться по ID");
        assertNotNull(foundEpic, "Эпик должен находиться по ID");
        assertNotNull(foundSubtask, "Подзадача должна находиться по ID");
        assertEquals(task.getTitle(), foundTask.getTitle(), "Названия задач должны совпадать");
        assertEquals(epic.getTitle(), foundEpic.getTitle(), "Названия эпиков должны совпадать");
        assertEquals(subtask.getTitle(), foundSubtask.getTitle(), "Названия подзадач должны совпадать");
    }

    @Test
    void shouldNotConflictBetweenGeneratedId() {
        Task taskWithGeneratedId = new Task(
                "Задача1",
                "Описание1",
                Status.NEW,
                20,
                LocalDateTime.of(2025, 8, 12, 8, 30)
        );
        manager.createTask(taskWithGeneratedId);
        Task taskWithGeneratedId2 = new Task(
                "Задача2",
                "Описание2",
                Status.NEW,
                120,
                LocalDateTime.of(2025, 6, 1, 12, 30)
        );
        manager.createTask(taskWithGeneratedId2);
        Task foundTask1 = manager.getTask(taskWithGeneratedId.getId());
        Task foundTask2 = manager.getTask(taskWithGeneratedId2.getId());

        assertNotNull(foundTask1, "Первая задача должна существовать");
        assertNotNull(foundTask2, "Вторая задача должна существовать");
        assertNotEquals(foundTask1.getId(), foundTask2.getId(), "Сгенерированные ID не должны совпадать");
        assertEquals("Задача1", foundTask1.getTitle());
        assertEquals("Задача2", foundTask2.getTitle());
    }

    @Test
    void shouldNotConflictBetweenGivenAndGeneratedId() {
        // Создаем несколько задач
        Task task1 = new Task(
                "Задача1",
                "Описание",
                Status.NEW,
                60,
                LocalDateTime.of(2025, 6, 1, 12, 30)
        );
        Task task2 = new Task(
                "Задача2",
                "Описание",
                Status.NEW,
                120,
                LocalDateTime.of(2025, 6, 1, 12, 30)
        );
        Task task3 = new Task(
                "Задача3",
                "Описание",
                Status.NEW,
                180,
                LocalDateTime.of(2025, 6, 1, 12, 30)
        );
        manager.createTask(task1);
        manager.createTask(task2);
        manager.createTask(task3);

        int initialCount = manager.getAllTasks().size();
        Task duplicateTask = new Task(
                "ЗадачаКопия",
                "Описание",
                task2.getId(),
                Status.NEW,
                120,
                LocalDateTime.of(2025, 6, 1, 12, 30)
        );
        manager.createTask(duplicateTask);

        assertEquals(initialCount, manager.getAllTasks().size(),
                "Количество задач не должно измениться при коллизии ID");
        Task foundTask = manager.getTask(task2.getId());
        assertEquals("Задача2", foundTask.getTitle());
    }

    @Test
    void shouldNotModifyTaskWhenAddingToManager() {
        // Создаем задачу
        Task task = new Task(
                "Задача",
                "Описание",
                Status.NEW,
                60,
                LocalDateTime.of(2025, 6, 1, 12, 30)
        );
        String taskTitle = task.getTitle();
        String taskDescription = task.getDescription();
        Status taskStatus = task.getStatus();
        manager.createTask(task);

        assertEquals(taskTitle, task.getTitle(), "Название не должно измениться");
        assertEquals(taskDescription, task.getDescription(), "Описание не должно измениться");
        assertEquals(taskStatus, task.getStatus(), "Статус не должен измениться");
        assertTrue(task.getId() > 0, "ID должен быть установлен");
    }

    @Test
    void shouldNotModifyEpicWhenAddingToManager() {
        Epic epic = new Epic("Эпик", "Описание эпика");
        String epicTitle = epic.getTitle();
        String epicDescription = epic.getDescription();
        Status epicStatus = epic.getStatus();
        manager.createEpic(epic);

        assertEquals(epicTitle, epic.getTitle(), "Название не должно измениться");
        assertEquals(epicDescription, epic.getDescription(), "Описание не должно измениться");
        assertEquals(epicStatus, epic.getStatus(), "Статус не должен измениться");
        assertTrue(epic.getId() > 0, "ID должен быть установлен");
    }

    @Test
    void shouldNotModifySubtaskWhenAddingToManager() {
        Epic epic = new Epic("Эпик", "Описание");
        manager.createEpic(epic);
        Subtask subtask = new Subtask(
                "Демонтаж стен",
                "",
                epic.getId(),
                Status.NEW,
                720,
                LocalDateTime.of(2025, 8, 12, 8, 30)
        );
        String subtaskTitle = subtask.getTitle();
        String subtaskDescription = subtask.getDescription();
        Status subtaskStatus = subtask.getStatus();
        int EpicId = subtask.getEpicId();
        manager.createSubtask(subtask);

        assertEquals(subtaskTitle, subtask.getTitle(), "Название не должно измениться");
        assertEquals(subtaskDescription, subtask.getDescription(), "Описание не должно измениться");
        assertEquals(subtaskStatus, subtask.getStatus(), "Статус не должно измениться");
        assertEquals(EpicId, subtask.getEpicId(), "EpicID не должен измениться");
        assertTrue(subtask.getId() > 0, "ID должен быть установлен");
    }


}