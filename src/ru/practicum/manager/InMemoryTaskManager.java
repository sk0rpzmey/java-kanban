package ru.practicum.manager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;


import ru.practicum.model.*;

public class InMemoryTaskManager implements TaskManager {
    protected final HashMap<Integer, Task> tasks = new HashMap<>();
    protected final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    protected final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HistoryManager historyManager = Manager.getDefaultHistory();
    private final Set<Task> prioritizedTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime));

    protected int id = 1;

    private int generateId() {
        return id++;
    }

    private void updateEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        List<Subtask> epicSubtasks = getSubtasksByEpicId(epicId);

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
        setPrioritizedTasks(task);
    }

    // Метод для обновления
    @Override
    public void updateTask(Task task) {
        if (task == null) {
            return;
        }

        deleteTaskInPrioritizedTasks(tasks.get(task.getId()));  // удаление прошлой задачи

        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
            setPrioritizedTasks(task);
        }
    }

    // Метод для удаления
    @Override
    public void deleteTask(int id) {
        deleteTaskInPrioritizedTasks(tasks.get(id));
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
        Optional.of(epics.remove(id))
                .ifPresent(epic -> {
                    epic.getSubtaskId().forEach(subtasksId -> {
                        deleteTaskInPrioritizedTasks(subtasks.get(subtasksId));
                        subtasks.remove(subtasksId);
                        historyManager.remove(subtasksId);
                    });
                    historyManager.remove(id);
                });
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

        if (subtask.getId() == 0) {
            subtask.setId(generateId());
        }
        if (subtask.getEpicId() == subtask.getId()) {
            return; //Подзадача не может быть своим же эпиком
        }

        if (subtasks.containsKey(subtask.getId())) {
            return; // Не добавляем, если ID уже существует
        }

        subtasks.put(subtask.getId(), subtask);
        setPrioritizedTasks(subtask);

        Epic epic = epics.get(subtask.getEpicId());
        epic.addSubtaskId(subtask.getId());
        updateEpicStatus(epic.getId()); // Обновляем статус эпика
        updateEpicDuration(subtask.getEpicId());  // Обновление продолжительности эпика
        updateEpicTime(subtask.getEpicId());
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

        deleteTaskInPrioritizedTasks(subtasks.get(subtask.getId()));  // удаление прошлой задачи

        if (subtasks.containsKey(subtask.getId())) {
            subtasks.put(subtask.getId(), subtask);
            setPrioritizedTasks(subtask);
            Epic epic = epics.get(subtask.getEpicId());
            updateEpicStatus(epic.getId());  // Обновляем статус эпика
            updateEpicDuration(subtask.getEpicId());  // Обновление продолжительности эпика
            updateEpicTime(subtask.getEpicId());  // Обновление времени начала и окончания эпика
        }
    }

    // Метод для удаления
    @Override
    public void deleteSubtask(int id) {
        Subtask subtask = subtasks.remove(id);
        deleteTaskInPrioritizedTasks(subtask);
        if (subtask != null) {
            Epic epic = epics.get(subtask.getEpicId());
            epic.removeSubtaskId(id);
            updateEpicStatus(epic.getId());
            updateEpicDuration(subtask.getEpicId());  // Обновление продолжительности эпика
            updateEpicTime(subtask.getEpicId());  // Обновление времени начала и окончания эпика
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
    public List<Subtask> getSubtasksByEpicId(int epicId) {
        return Optional.ofNullable(epics.get(epicId))
                .map(Epic::getSubtaskId)
                .map(ids -> ids.stream()
                        .map(subtasks::get)
                        .filter(Objects::nonNull)
                        .toList())
                .orElse(Collections.emptyList());
    }

    // Методы для очистки всего хранилища определенного типа
    @Override
    public void deleteAllTasks() {
        tasks.keySet().forEach(historyManager::remove);
        tasks.clear();

    }

    @Override
    public void deleteAllEpics() {
        subtasks.keySet().forEach(subtasksId -> {
            deleteTaskInPrioritizedTasks(subtasks.get(subtasksId));
        });
        subtasks.keySet().forEach(historyManager::remove);
        epics.keySet().forEach(historyManager::remove);
        subtasks.clear();
        epics.clear();
    }

    @Override
    public void deleteAllSubtasks() {
        epics.values().forEach(epic -> {
            epic.getSubtaskId().clear();
            epic.setStatus(Status.NEW);
        });
        epics.keySet().forEach(historyManager::remove);
        subtasks.clear();
    }

    private void updateEpicDuration(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic == null) {
            return;
        }
        Duration total = getSubtasksByEpicId(epicId).stream()
                .map(Subtask::getDuration)
                .reduce(Duration.ZERO, Duration::plus);
        epic.setDuration(total);
    }

    private void updateEpicTime(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic == null) {
            return;
        }

        LocalDateTime startTime = LocalDateTime.MAX;
        LocalDateTime endTime = LocalDateTime.MIN;

        for (Subtask subtask : getSubtasksByEpicId(epicId)) {
            LocalDateTime start = subtask.getStartTime();
            LocalDateTime end = subtask.getEndTime();

            if (start == null && end == null) {
                continue;
            }


            if (start.isBefore(startTime)) {
                startTime = start;
            }

            if (end.isAfter(endTime)) {
                endTime = end;
            }
        }
        epic.setStartTime(startTime);
        epic.setEndTime(endTime);
    }

    private void setPrioritizedTasks(Task task) {
        if (task == null) {
            return;
        }

        if (task.getStartTime() == null || task.getDuration() == null) {
            return;
        }

        if (isTaskCrosses(task)) {
            throw new IllegalArgumentException("Задача: " + task + " пересекается по времени с другой задачей.");
        }

        prioritizedTasks.add(task);
    }

    private void deleteTaskInPrioritizedTasks(Task task) {
        if (task == null) {
            return;
        }
        prioritizedTasks.remove(task);
    }

    @Override
    public List<Task> getPrioritizedTasks() {
        return new ArrayList<>(prioritizedTasks);
    }

    private boolean isTaskCrosses(Task task) {
        if (task.getStartTime() == null || task.getDuration() == null) {
            return false;
        }

        LocalDateTime taskStartTime = task.getStartTime();
        LocalDateTime taskEndTime = task.getEndTime();

        return prioritizedTasks.stream()
                .anyMatch(taskToCompare -> {
                    LocalDateTime taskToCompareStartTime = taskToCompare.getStartTime();
                    LocalDateTime taskToCompareEndTime = taskToCompare.getEndTime();
                    return taskStartTime.isBefore(taskToCompareEndTime) && taskToCompareStartTime.isBefore(taskEndTime);
                });
    }
}
