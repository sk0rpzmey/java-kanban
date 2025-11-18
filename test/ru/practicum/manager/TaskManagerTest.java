package ru.practicum.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

abstract class TaskManagerTest <T extends TaskManager> {

    @BeforeEach
    abstract void setUp();

    @Test
    void shouldReturnInitializedManagers() {
        // Проверяем, что getDefault() возвращает готовый к работе TaskManager
        TaskManager taskManager = Manager.getDefault();
        assertNotNull(taskManager, "TaskManager не должен быть null");

        HistoryManager historyManager = Manager.getDefaultHistory();
        assertNotNull(historyManager, "HistoryManager не должен быть null");
    }
}