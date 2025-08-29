package ru.practicum.manager;

import ru.practicum.model.Task;

import java.util.ArrayList;
import java.util.List;

public interface HistoryManager {

    void addViewedTasks(Task task);

    List<Task> getHistory();
}
