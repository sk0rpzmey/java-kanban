package ru.practicum.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.model.*;


import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    private TaskManager manager;

    @BeforeEach
    void setUp() {
        manager = Manager.getDefault();
    }

    @Test
    void shouldAddAndFindDifferentTaskTypes() {
        Task task = new Task("Задача", "Описание задачи", Status.NEW);
        manager.createTask(task);
        Epic epic = new Epic("Эпик", "Описание эпика");
        manager.createEpic(epic);
        Subtask subtask = new Subtask("Подзадача", "Описание подзадачи", epic.getId(), Status.NEW);
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
        Task taskWithGeneratedId = new Task("Задача1", "Описание1", Status.NEW);
        manager.createTask(taskWithGeneratedId);
        Task taskWithGeneratedId2 = new Task("Задача2", "Описание2", Status.NEW);
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
        Task task1 = new Task("Задача1", "Описание", Status.NEW);
        Task task2 = new Task("Задача2", "Описание", Status.NEW);
        Task task3 = new Task("Задача3", "Описание", Status.NEW);
        manager.createTask(task1);
        manager.createTask(task2);
        manager.createTask(task3);

        int initialCount = manager.getAllTasks().size();
        Task duplicateTask = new Task("ЗадачаКопия", "Описание", task2.getId(), Status.NEW);
        manager.createTask(duplicateTask);

        assertEquals(initialCount, manager.getAllTasks().size(),
                "Количество задач не должно измениться при коллизии ID");
        Task foundTask = manager.getTask(task2.getId());
        assertEquals("Задача2", foundTask.getTitle());
    }

    @Test
    void shouldNotModifyTaskWhenAddingToManager() {
        // Создаем задачу
        Task Task = new Task("Задача", "Описание", Status.NEW);
        String taskTitle = Task.getTitle();
        String taskDescription = Task.getDescription();
        Status taskStatus = Task.getStatus();
        manager.createTask(Task);

        assertEquals(taskTitle, Task.getTitle(), "Название не должно измениться");
        assertEquals(taskDescription, Task.getDescription(), "Описание не должно измениться");
        assertEquals(taskStatus, Task.getStatus(), "Статус не должен измениться");
        assertTrue(Task.getId() > 0, "ID должен быть установлен");
    }

    @Test
    void shouldNotModifyEpicWhenAddingToManager() {
        Epic Epic = new Epic("Эпик", "Описание эпика");
        String epicTitle = Epic.getTitle();
        String epicDescription = Epic.getDescription();
        Status epicStatus = Epic.getStatus();
        manager.createEpic(Epic);

        assertEquals(epicTitle, Epic.getTitle(), "Название не должно измениться");
        assertEquals(epicDescription, Epic.getDescription(), "Описание не должно измениться");
        assertEquals(epicStatus, Epic.getStatus(), "Статус не должен измениться");
        assertTrue(Epic.getId() > 0, "ID должен быть установлен");
    }

    @Test
    void shouldNotModifySubtaskWhenAddingToManager() {
        Epic epic = new Epic("Эпик", "Описание");
        manager.createEpic(epic);
        Subtask Subtask = new Subtask("Подзадача", "Описание", epic.getId(), Status.NEW);
        String subtaskTitle = Subtask.getTitle();
        String subtaskDescription = Subtask.getDescription();
        Status subtaskStatus = Subtask.getStatus();
        int EpicId = Subtask.getEpicId();
        manager.createSubtask(Subtask);

        assertEquals(subtaskTitle, Subtask.getTitle(), "Название не должно измениться");
        assertEquals(subtaskDescription, Subtask.getDescription(), "Описание не должно измениться");
        assertEquals(subtaskStatus, Subtask.getStatus(), "Статус не должно измениться");
        assertEquals(EpicId, Subtask.getEpicId(), "EpicID не должен измениться");
        assertTrue(Subtask.getId() > 0, "ID должен быть установлен");
    }


}