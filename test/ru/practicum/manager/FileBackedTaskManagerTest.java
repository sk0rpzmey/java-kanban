package ru.practicum.manager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

import ru.practicum.model.*;

public class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTaskManager>{
    private FileBackedTaskManager manager;
    @TempDir
    Path tempDir; // Автоматически создаст директорию

    @BeforeEach
    void setUp()  {
        manager = new FileBackedTaskManager(Paths.get("test_tasks.csv").toFile());
    }

    @AfterEach
    void cleanup() throws IOException {
        // удалить временный файл после теста
        Files.deleteIfExists(Paths.get("test_tasks.csv"));
    }

    @Test
    void shouldLoadFromFileWithEmptyFile() throws IOException {
        Task task = new Task(
                "Помыть машину",
                "Полная мойка с полировкой",
                Status.NEW,
                20,
                LocalDateTime.of(2025, 9, 12, 8, 30)
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

        // Проверяем, что файл был создан
        File file = new File("test_tasks.csv");
        assertTrue(file.exists());


        // Проверяем задачу
        assertEquals(1, manager.getAllTasks().size());
        Task loadedTask = manager.getAllTasks().getFirst();
        assertEquals(task.getId(), loadedTask.getId());
        assertEquals(task.getStatus(), loadedTask.getStatus());
        assertEquals(task.getDescription(), loadedTask.getDescription());
        assertEquals(task.getTitle(), loadedTask.getTitle());

        // Проверяем эпик
        assertEquals(1, manager.getAllEpics().size());
        Epic loadedEpic = manager.getAllEpics().getFirst();
        assertEquals(epic.getId(), loadedEpic.getId());
        assertEquals(epic.getStatus(), loadedEpic.getStatus());
        assertEquals(epic.getDescription(), loadedEpic.getDescription());
        assertEquals(epic.getTitle(), loadedEpic.getTitle());
        // Проверяем подзадачи эпика
        assertEquals(epic.getSubtaskId(), loadedEpic.getSubtaskId());

        // Проверяем подзадачу
        assertEquals(1, manager.getAllSubtasks().size());
        Subtask loadedSubtask = manager.getAllSubtasks().getFirst();
        assertEquals(subtask.getId(), loadedSubtask.getId());
        assertEquals(subtask.getStatus(), loadedSubtask.getStatus());
        assertEquals(subtask.getDescription(), loadedSubtask.getDescription());
        assertEquals(subtask.getTitle(), loadedSubtask.getTitle());
        assertEquals(subtask.getEpicId(), loadedSubtask.getEpicId()); // проверяем epicId подзадачи
    }

    @Test
    void shouldLoadFromFileWithTasks() throws IOException {
        File tempFile = tempDir.resolve("tasks.csv").toFile();

        // Записываем данные в файл
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("id;type;name;status;description;duration;startTime;endTime;epic\n");
            writer.write("1;TASK;Помыть машину;NEW;Полная мойка с полировкой;PT2H;2025-06-01T12:30;2025-06-01T14:30;\n");
            writer.write("2;EPIC;Ремонт квартиры;NEW;Полный цикл работ;PT12H;2025-08-12T08:30;2025-09-01T12:10;\n");
            writer.write("3;SUBTASK;Демонтаж стен;NEW;;PT12H;2025-08-12T08:30;2025-08-12T20:30;2\n");
        }

        FileBackedTaskManager manager = FileBackedTaskManager.loadFromFile(tempFile);

        assertEquals(1, manager.getAllTasks().size());
        assertEquals(1, manager.getAllEpics().size());
        assertEquals(1, manager.getAllSubtasks().size());
    }

    @Test
    void shouldSaveToFile() throws IOException {
        File tempFile = tempDir.resolve("tasks.csv").toFile();

        FileBackedTaskManager manager = new FileBackedTaskManager(tempFile);

        Task task = new Task(
                "Помыть машину",
                "Полная мойка с полировкой",
                Status.NEW,
                20,
                LocalDateTime.of(2025, 9, 12, 8, 30)
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

        // Проверяем, что файл был создан и содержит данные
        assertTrue(tempFile.exists());

        // Проверяем данные
        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(tempFile);

        assertEquals(1, loadedManager.getAllTasks().size());
        assertEquals(1, loadedManager.getAllEpics().size());
        assertEquals(1, loadedManager.getAllSubtasks().size());
    }
}
