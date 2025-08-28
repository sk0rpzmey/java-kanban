package ru.practicum.manager;

import ru.practicum.model.*;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {
    private final ArrayList<Task> listViewedTasks = new ArrayList<>();
    private final static int LIMIT_LIST_VIEWED_TASKS = 10;

    // Методы для работы с историей просмотров
    public void addViewedTasks(Task task) {
        if (task == null) {
            return;
        }
        if (listViewedTasks.size() == LIMIT_LIST_VIEWED_TASKS) {
            listViewedTasks.removeFirst();
        }
        if (task instanceof Epic) {
            listViewedTasks.add(new Epic((Epic) task));
        } else if (task instanceof Subtask) {
            listViewedTasks.add(new Subtask((Subtask) task));
        } else {
            listViewedTasks.add(new Task(task));
        }
    }

    @Override
    public ArrayList<Task> getHistory() {
        return listViewedTasks;
    }
}
