package ru.practicum.manager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

import ru.practicum.model.*;

public class FileBackedTaskManagerTest {
    @TempDir
    Path tempDir; // Автоматически создаст директорию

    @Test
    void shouldLoadFromFileWithEmptyFile() throws IOException {
        File tempFile = tempDir.resolve("tasks.csv").toFile();
        // Создаем пустой файл
        tempFile.createNewFile();

        FileBackedTaskManager manager = FileBackedTaskManager.loadFromFile(tempFile);

        assertTrue(manager.getAllTasks().isEmpty());
        assertTrue(manager.getAllEpics().isEmpty());
        assertTrue(manager.getAllSubtasks().isEmpty());
    }

    @Test
    void shouldLoadFromFileWithTasks() throws IOException {
        File tempFile = tempDir.resolve("tasks.csv").toFile();

        // Записываем данные в файл
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("id,type,name,status,description,epic\n");
            writer.write("1,TASK,Помыть машину,NEW,Полная мойка с полировкой,\n");
            writer.write("2,EPIC,Ремонт квартиры,NEW,Полный цикл работ,\n");
            writer.write("3,SUBTASK,Демонтаж стен,NEW,,2\n");
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

        Task task = new Task("Помыть машину", "Полная мойка с полировкой", Status.NEW);
        manager.createTask(task);

        Epic epic = new Epic("Ремонт квартиры", "Полный цикл работ");
        manager.createEpic(epic);

        Subtask subtask = new Subtask("Демонтаж стен", "", 2, Status.NEW);
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
