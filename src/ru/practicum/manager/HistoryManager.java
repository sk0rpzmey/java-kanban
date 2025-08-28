package ru.practicum.manager;

import ru.practicum.model.Task;

import java.util.ArrayList;

public interface HistoryManager {

    void addViewedTasks(Task task);

    ArrayList<Task> getHistory();
}
