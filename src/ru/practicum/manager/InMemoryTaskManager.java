package ru.practicum.manager;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

import ru.practicum.model.*;

public class InMemoryTaskManager implements TaskManager {
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HistoryManager historyManager = Manager.getDefaultHistory();

    private int id = 1;

    private int generateId() {
        return id++;
    }

    private void updateEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        ArrayList<Subtask> epicSubtasks = getSubtasksByEpicId(epicId);

        if (epicSubtasks.isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }

        boolean allDone = true;
        boolean allNew = true;

        for (Subtask subtask : epicSubtasks) {
            if (subtask.getStatus() != Status.DONE) allDone = false;
            if (subtask.getStatus() != Status.NEW) allNew = false;
        }

        if (allDone) epic.setStatus(Status.DONE);
        else if (allNew) epic.setStatus(Status.NEW);
        else epic.setStatus(Status.IN_PROGRESS);
    }

    // Методы для Task
    @Override
    public void createTask(Task task) {
        if (task == null) {
            return;
        }
        if (task.getId() == 0) {
            task.setId(generateId());
        }
        if (tasks.containsKey(task.getId())) {
            return; // Не добавляем, если ID уже существует
        }

        tasks.put(task.getId(), task);
    }

    // Метод для обновления
    @Override
    public void updateTask(Task task) {
        if (task == null) {
            return;
        }
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        }
    }

    // Метод для удаления
    @Override
    public void deleteTask(int id) {
        tasks.remove(id);
        historyManager.remove(id);
    }

    // Методы для Epic
    @Override
    public void createEpic(Epic epic) {
        if (epic == null) {
            return;
        }
        if (epic.getId() == 0) { // ← Добавьте эту проверку
            epic.setId(generateId());
        }
        if (epics.containsKey(epic.getId())) {
            return; // Не добавляем, если ID уже существует
        }
        epics.put(epic.getId(), epic);
    }

    // Метод для обновления
    @Override
    public void updateEpic(Epic epic) {
        if (epic == null) {
            return;
        }
        if (epics.containsKey(epic.getId())) {
            Epic savedEpic = epics.get(epic.getId());
            savedEpic.setTitle(epic.getTitle());
            savedEpic.setDescription(epic.getDescription());
        }
    }

    // Метод для удаления
    @Override
    public void deleteEpic(int id) {
        Epic epic = epics.remove(id);
        if (epic != null) {
            for (int subtaskId : epic.getSubtaskId()) {
                subtasks.remove(subtaskId);
                historyManager.remove(subtaskId);
            }
            historyManager.remove(id);
        }
    }

    // Методы для Subtask
    @Override
    public void createSubtask(Subtask subtask) {
        if (subtask == null) {
            return;
        }

        if (!epics.containsKey(subtask.getEpicId())) {
            return;
        }

        if (subtask.getId() == 0) { // ← Добавьте эту проверку
            subtask.setId(generateId());
        }
        if (subtask.getEpicId() == subtask.getId()) {
            return; //Подзадача не может быть своим же эпиком
        }

        if (subtasks.containsKey(subtask.getId())) {
            return; // Не добавляем, если ID уже существует
        }

        subtasks.put(subtask.getId(), subtask);

        Epic epic = epics.get(subtask.getEpicId());
        epic.addSubtaskId(subtask.getId());
        updateEpicStatus(epic.getId()); // Обновляем статус эпика
    }

    // Метод для обновления
    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtask == null) { // Проверка существования эпика
            return;
        }
        if (subtask.getEpicId() == subtask.getId()) {
            return; //Подзадача не может быть своим же эпиком
        }
        if (!epics.containsKey(subtask.getEpicId())) {
            return;
        }
        if (subtasks.containsKey(subtask.getId())) {
            subtasks.put(subtask.getId(), subtask);
            Epic epic = epics.get(subtask.getEpicId());
            updateEpicStatus(epic.getId());
        }
    }

    // Метод для удаления
    @Override
    public void deleteSubtask(int id) {
        Subtask subtask = subtasks.remove(id);
        if (subtask != null) {
            Epic epic = epics.get(subtask.getEpicId());
            epic.removeSubtaskId(id);
            updateEpicStatus(epic.getId());
            historyManager.remove(id);
        }
    }

    // Методы для просмотра задач по id
    @Override
    public Task getTask(int id) {
        Task task = tasks.get(id);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }

    @Override
    public Subtask getSubtask(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask != null) {
            historyManager.add(subtask);
        }
        return subtask;
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            historyManager.add(epic);
        }
        return epic;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    // Получение списков
    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public ArrayList<Subtask> getSubtasksByEpicId(int epicId) {
        ArrayList<Subtask> result = new ArrayList<>();
        Epic epic = epics.get(epicId);
        if (epic != null) {
            for (int subtaskId : epic.getSubtaskId()) {
                result.add(subtasks.get(subtaskId));
            }
        }
        return result;
    }

    // Методы для очистки всего хранилища определенного типа
    @Override
    public void deleteAllTasks() {
        for (int taskId : tasks.keySet()) {
            historyManager.remove(taskId);
        }
        tasks.clear();
    }

    @Override
    public void deleteAllEpics() {
        for (int subtaskId : subtasks.keySet()) {
            historyManager.remove(subtaskId);
        }

        for (int epicId : epics.keySet()) {
            historyManager.remove(epicId);
        }
        subtasks.clear();
        epics.clear();
    }

    @Override
    public void deleteAllSubtasks() {
        for (Epic epic : epics.values()) {
            epic.getSubtaskId().clear();
            epic.setStatus(Status.NEW);
        }

        for (int subtaskId : subtasks.keySet()) {
            historyManager.remove(subtaskId);
        }
        subtasks.clear();
    }
}
