package ru.practicum.manager;

import ru.practicum.model.Epic;
import ru.practicum.model.Subtask;
import ru.practicum.model.Task;

import java.util.List;

public interface TaskManager {
    // Методы для Task
    void createTask(Task task);

    // Метод для обновления
    void updateTask(Task task);

    // Метод для удаления
    void deleteTask(int id);

    // Методы для Epic
    void createEpic(Epic epic);

    // Метод для обновления
    void updateEpic(Epic epic);

    // Метод для удаления
    void deleteEpic(int id);

    // Методы для Subtask
    void createSubtask(Subtask subtask);

    // Метод для обновления
    void updateSubtask(Subtask subtask);

    // Метод для удаления
    void deleteSubtask(int id);

    // Методы для просмотра задач по id
    Task getTask(int id);

    Subtask getSubtask(int id);

    Epic getEpic(int id);

    List<Task> getHistory();

    // Получение списков
    List<Task> getAllTasks();

    List<Epic> getAllEpics();

    List<Subtask> getAllSubtasks();

    List<Subtask> getSubtasksByEpicId(int epicId);

    // Методы для очистки всего хранилища определенного типа
    void deleteAllTasks();

    void deleteAllEpics();

    void deleteAllSubtasks();
}
