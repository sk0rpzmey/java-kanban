package ru.practicum.manager;

import ru.practicum.model.*;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    //private final ArrayList<Task> listViewedTasks = new ArrayList<>();  // список для вывода
    // здесь хранится история без повторов
    private final Map<Integer, Task> uniqueBrowsingHistory = new LinkedHashMap<>();

    // Методы для работы с историей просмотров
    @Override
    public void addViewedTasks(Task task) {
        if (task == null) {
            return;
        }

        int taskId = task.getId();
        if (uniqueBrowsingHistory.containsKey(taskId)) {
            remove(taskId);
        }

        if (task instanceof Epic) {
            uniqueBrowsingHistory.put(taskId, new Epic((Epic) task));
        } else if (task instanceof Subtask) {
            uniqueBrowsingHistory.put(taskId, new Subtask((Subtask) task));
        } else {
            uniqueBrowsingHistory.put(taskId, new Task(task));
        }
    }

    @Override
    public void remove(int id) {
        uniqueBrowsingHistory.remove(id);
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(uniqueBrowsingHistory.values());
    }

    @Override
    public void clearHistory() {
        uniqueBrowsingHistory.clear();
    }
}
