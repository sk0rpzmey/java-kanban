package ru.practicum.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.model.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

abstract class TaskManagerTest<T extends TaskManager> {
    T manager;
    Task task1;
    Epic epic1;
    Subtask subtask1;

    @BeforeEach
    abstract void setUp();

    @Test
    void shouldReturnInitializedManagers() {
        // Проверяем, что getDefault() возвращает готовый к работе TaskManager
        TaskManager taskManager = Manager.getDefault();
        assertNotNull(taskManager, "TaskManager не должен быть null");

        HistoryManager historyManager = Manager.getDefaultHistory();
        assertNotNull(historyManager, "HistoryManager не должен быть null");
    }

    @Test
    void shouldCreateAndGetTask() {
        manager.createTask(task1);
        Task taskForCheck = manager.getTask(task1.getId());
        assertNotNull(taskForCheck);
        assertEquals(taskForCheck.getTitle(), task1.getTitle());
    }

    @Test
    void shouldUpdateTask() {
        manager.createTask(task1);
        task1.setTitle("Задача12");
        manager.updateTask(task1);
        assertNotEquals("Задача1", task1.getTitle());
    }

    @Test
    void shouldDeleteTask() {
        manager.createTask(task1);
        manager.deleteTask(task1.getId());
        assertEquals(0, manager.getAllTasks().size());
    }

    @Test
    void shouldCreateAndGetEpic() {
        manager.createEpic(epic1);
        Epic epicForCheck = manager.getEpic(epic1.getId());
        assertNotNull(epicForCheck);
        assertEquals(epicForCheck.getTitle(), epic1.getTitle());
    }

    @Test
    void shouldUpdateEpic() {
        manager.createEpic(epic1);
        epic1.setTitle("Эпик12");
        manager.updateEpic(epic1);
        assertNotEquals("Эпик1", epic1.getTitle());
    }

    @Test
    void shouldDeleteEpic() {
        manager.createEpic(epic1);
        manager.deleteEpic(epic1.getId());
        assertEquals(0, manager.getAllEpics().size());
    }

    @Test
    void shouldCreateAndGetSubtask() {
        manager.createEpic(epic1);
        manager.createSubtask(subtask1);
        Subtask subtaskForCheck = manager.getSubtask(subtask1.getId());
        assertNotNull(subtaskForCheck);
        assertEquals(subtaskForCheck.getTitle(), subtask1.getTitle());
    }

    @Test
    void shouldUpdateSubtask() {
        manager.createSubtask(subtask1);
        subtask1.setTitle("Сабтаск12");
        manager.updateSubtask(subtask1);
        assertNotEquals("Сабтаск1", subtask1.getTitle());
    }

    @Test
    void shouldDeleteSubtask() {
        manager.createSubtask(subtask1);
        manager.deleteSubtask(subtask1.getId());
        assertEquals(0, manager.getAllSubtasks().size());
    }

    @Test
    void shouldBeOneInListTasksSubtasksEpics() {
        manager.createTask(task1);

        manager.createEpic(epic1);
        manager.createSubtask(subtask1);
        assertEquals(1, manager.getAllTasks().size());
        assertEquals(1, manager.getAllEpics().size());
        assertEquals(1, manager.getAllSubtasks().size());
    }

    @Test
    void shouldBeOneWhenGetSubtasksByEpicId() {
        manager.createEpic(epic1);
        manager.createSubtask(subtask1);
        assertEquals(1, manager.getSubtasksByEpicId(epic1.getId()).size());
    }

    @Test
    void shouldBeZeroAfterDeleteTasksSubtasksEpics() {
        manager.createTask(task1);
        manager.createEpic(epic1);
        manager.createSubtask(subtask1);
        manager.deleteAllTasks();
        manager.deleteAllEpics();
        manager.deleteAllSubtasks();
        assertEquals(0, manager.getAllTasks().size());
        assertEquals(0, manager.getAllEpics().size());
        assertEquals(0, manager.getAllSubtasks().size());
    }

    // должен вернуть 2, потому что в список должны попадать только таски и сабтаски
    @Test
    void shouldBeReturnSizeTwoInPrioritizedTasksList() {
        manager.createTask(task1);
        manager.createEpic(epic1);
        manager.createSubtask(subtask1);
        assertEquals(2, manager.getPrioritizedTasks().size());
    }
}