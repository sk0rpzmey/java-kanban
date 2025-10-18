package ru.practicum.manager;

import java.util.*;
import java.io.*;
import java.nio.file.Files;
import ru.practicum.model.*;

public class FileBackedTaskManager extends InMemoryTaskManager{
    private final File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
    }


    @Override
    public void createTask(Task task) {
        super.createTask(task);
        save();
    }

    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        save();
    }

    @Override
    public void createSubtask(Subtask subtask) {
        super.createSubtask(subtask);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void deleteTask(int id) {
        super.deleteTask(id);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void deleteEpic(int id) {
        super.deleteEpic(id);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void deleteSubtask(int id) {
        super.deleteSubtask(id);
        save();
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public void deleteAllSubtasks() {
        super.deleteAllSubtasks();
        save();
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager manager = new FileBackedTaskManager(file);

        try {
            List<String> lines = Files.readAllLines(file.toPath());
            if (lines.isEmpty()) {
                return manager;
            }

            // Пропускаем заголовок
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i);
                if (line.trim().isEmpty()) {
                    continue;
                }

                Task task = fromString(line);
                if (task != null) {
                    if (task instanceof Epic) {
                        manager.createEpic((Epic) task);
                    } else if (task instanceof Subtask) {
                        manager.createSubtask((Subtask) task);
                    } else {
                        manager.createTask(task);
                    }
                }
            }

        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при чтении файла", e);
        }

        return manager;
    }

    private static Task fromString(String value) {
        String[] parts = value.split(",");
        if (parts.length < 5) {
            return null;
        }

        try {
            int id = Integer.parseInt(parts[0]);
            String type = parts[1];
            String title = parts[2];
            Status status = Status.valueOf(parts[3]);
            String description = parts[4];

            switch (type) {
                case "TASK":
                    Task task = new Task(title, description, id, status);
                    return task;
                case "EPIC":
                    Epic epic = new Epic(title, description, id);
                    epic.setStatus(status);
                    return epic;
                case "SUBTASK":
                    int epicId = 0;
                    if (parts.length > 5 && !parts[5].isEmpty()) {
                        epicId = Integer.parseInt(parts[5]);
                    }
                    Subtask subtask = new Subtask(title, description, epicId, status);
                    subtask.setId(id);
                    subtask.setStatus(status);
                    return subtask;
                default:
                    return null;
            }
        } catch (IllegalArgumentException e) {
            throw new ManagerSaveException("Ошибка при разборе строки", e);
        }
    }

    public void save() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))){
            writer.println("id,type,name,status,description,epic");

            for (Task task : getAllTasks()) {
                writer.println(toString(task));
            }

            for (Epic epic : getAllEpics()) {
                writer.println(toString(epic));
            }

            for (Subtask subtask : getAllSubtasks()) {
                writer.println(toString(subtask));
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка сохранения в файл", e);
        }
    }

    private String toString(Task task) {
        if (task instanceof Subtask) {
            Subtask subtask = (Subtask) task;
            return String.format("%d,%s,%s,%s,%s,%d",
                    task.getId(),
                    TaskType.SUBTASK,
                    task.getTitle(),
                    task.getStatus(),
                    task.getDescription(),
                    subtask.getEpicId());
        } else if (task instanceof Epic) {
            return String.format("%d,%s,%s,%s,%s,",
                    task.getId(),
                    TaskType.EPIC,
                    task.getTitle(),
                    task.getStatus(),
                    task.getDescription());
        } else {
            return String.format("%d,%s,%s,%s,%s,",
                    task.getId(),
                    TaskType.TASK,
                    task.getTitle(),
                    task.getStatus(),
                    task.getDescription());
        }
    }
}
