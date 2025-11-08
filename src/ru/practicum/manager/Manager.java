package ru.practicum.manager;

import java.io.File;

public class Manager {
    private Manager() {
    }

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

//    public static FileBackedTaskManager getDefaultFileTaskManager(File file) {
//        return new FileBackedTaskManager(new File(file.toURI()));
//    }
}
