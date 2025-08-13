import java.util.HashMap;
import java.util.ArrayList;

public class TaskManager {
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private int id = 1;

    private int generateId() {
        return id++;
    }

    // Методы для Task
    public Task createTask(String title, String description, Status status) {
        int id = generateId();
        Task task = new Task(title, description, id, status);
        tasks.put(id, task);
        return task;
    }

    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        }
    }

    public void deleteTask(int id) {
        tasks.remove(id);
    }

    // Методы для Epic
    public Epic createEpic(String title, String description) {
        int id = generateId();
        Epic epic = new Epic(title, description, id);
        epics.put(id, epic);
        return epic;
    }

    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            Epic savedEpic = epics.get(epic.getId());
            savedEpic.setTitle(epic.getTitle());
            savedEpic.setDescription(epic.getDescription());
        }
    }

    public void deleteEpic(int id) {
        Epic epic = epics.remove(id);
        if (epic != null) {
            for (int subtaskId : epic.getSubtaskId()) {
                subtasks.remove(subtaskId);
            }
        }
    }

    // Методы для Subtask
    public Subtask createSubtask(String title, String description, Status status, int epicId) {
        if (!epics.containsKey(epicId)) return null; // Проверка существования эпика

        Subtask subtask = new Subtask(title, description, epicId, status);
        subtask.setId(generateId());
        subtasks.put(subtask.getId(), subtask);

        Epic epic = epics.get(epicId);
        epic.addSubtaskId(subtask.getId());
        epic.updateStatus(getSubtasksByEpicId(epicId)); // Обновляем статус эпика

        return subtask;
    }

    public void updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            subtasks.put(subtask.getId(), subtask);
            Epic epic = epics.get(subtask.getEpicId());
            epic.updateStatus(getSubtasksByEpicId(epic.getId()));
        }
    }

    public void deleteSubtask(int id) {
        Subtask subtask = subtasks.remove(id);
        if (subtask != null) {
            Epic epic = epics.get(subtask.getEpicId());
            epic.removeSubtaskId(id);
            epic.updateStatus(getSubtasksByEpicId(epic.getId()));
        }
    }

    // Получение списков
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public ArrayList<Task> getAllTasksWithSubtasksAndEpics() {
        ArrayList<Task> allTasks = new ArrayList<>();
        allTasks.addAll(getAllTasks());    // Обычные задачи
        allTasks.addAll(getAllEpics()); // Подзадачи
        allTasks.addAll(getAllSubtasks());    // Эпики
        return allTasks;
    }

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
}
