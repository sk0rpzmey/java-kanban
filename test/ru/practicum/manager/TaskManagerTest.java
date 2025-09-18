package ru.practicum.manager;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskManagerTest {
    @Test
    void shouldReturnInitializedManagers() {
        // Проверяем, что getDefault() возвращает готовый к работе TaskManager
        TaskManager taskManager = Manager.getDefault();
        assertNotNull(taskManager, "TaskManager не должен быть null");

        HistoryManager historyManager = Manager.getDefaultHistory();
        assertNotNull(historyManager, "HistoryManager не должен быть null");
    }
}