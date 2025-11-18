package ru.practicum;

import ru.practicum.manager.*;
import ru.practicum.model.*;

import java.io.File;
import java.time.LocalDateTime;


public class Main {
    public static void main(String[] args) {

//        TaskManager manager = Manager.getDefaultFileTaskManager(new File("src//test.csv"));
//        File tmpFile = new File("src", "test.csv");

//        FileBackedTaskManager fileManager = FileBackedTaskManager.loadFromFile(tmpFile);
//
//        System.out.println(fileManager.getAllTasks());
//        System.out.println(fileManager.getAllSubtasks());
//        System.out.println(fileManager.getAllEpics());

//        HistoryManager historyManager = Manager.getDefaultHistory();
//
//        // Создаем две обычные задачи
//        Task task1 = new Task(
//                "Помыть машину",
//                "Полная мойка с полировкой",
//                Status.NEW,
//                120,
//                LocalDateTime.of(2025, 6, 1, 12, 30)
//        );
//        Task task2 = new Task(
//                "Купить продукты",
//                "Молоко, хлеб, яйца",
//                Status.NEW
//        );
//        manager.createTask(task1);
//        manager.createTask(task2);
//
//        // Создаем первый эпик с двумя подзадачами
//        Epic epic1 = new Epic("Ремонт квартиры", "Полный цикл работ");
//        manager.createEpic(epic1);
//
//        Subtask subtask1 = new Subtask(
//                "Демонтаж стен",
//                "",
//                epic1.getId(),
//                Status.NEW,
//                720,
//                LocalDateTime.of(2025, 7, 12, 8, 30)
//        );
//        Subtask subtask2 = new Subtask(
//                "Укладка плитки",
//                "",
//                epic1.getId(),
//                Status.NEW,
//                220,
//                LocalDateTime.of(2025, 6, 12, 8, 30));
//        manager.createSubtask(subtask1);
//        manager.createSubtask(subtask2);
//
//        // Создаем второй эпик с одной подзадачей
//        Epic epic2 = new Epic("Подготовка к отпуску", "");
//        manager.createEpic(epic2);
//
//
//        Subtask subtask3 = new Subtask(
//                "Купить билеты",
//                "",
//                epic2.getId(),
//                Status.DONE,
//                10,
//                LocalDateTime.of(2025, 7, 12, 7, 30)
//        );
//        manager.createSubtask(subtask3);

//         Распечатываем списки задач
//        System.out.println("Список задач.");
//        System.out.println(manager.getAllTasks());
//        System.out.println("Список эпиков.");
//        System.out.println(manager.getAllEpics());
//        System.out.println("Список подзадач.");
//        System.out.println(manager.getAllSubtasks());
//        System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-");
//        System.out.println(manager.getPrioritizedTasks());


//        // Изменяем статусы
//        task1.setStartTime(LocalDateTime.of(2025, 6, 1, 13, 30));
//        manager.updateTask(task1);
//
//        System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-");
//        System.out.println(manager.getPrioritizedTasks());
//        subtask1.setStatus(Status.IN_PROGRESS);
//        manager.updateSubtask(subtask1);
//
//        subtask3.setStatus(Status.DONE);
//        manager.updateSubtask(subtask3);
//
//        System.out.println("Список эпиков после изменения статуса.");
//        System.out.println(manager.getAllEpics());
//
//        // Удаляем задачу и эпик
//        manager.deleteSubtask(subtask1.getId());
//        System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-");
//        System.out.println(manager.getPrioritizedTasks());
//        manager.deleteEpic(epic1.getId());
//
        // Распечатываем списки задач и эпиков
//        System.out.println("Списки после удаления.");
//        System.out.println("Список задач.");
//        System.out.println(manager.getAllTasks());
//        System.out.println("Список эпиков.");
//        System.out.println(manager.getAllEpics());
//        System.out.println("Список подзадач.");
//        System.out.println(manager.getAllSubtasks());
//
//        // Проверка истории просмотров
//        System.out.println("-------------");
//        System.out.println(manager.getTask(2));
//        System.out.println(manager.getEpic(6));
//        System.out.println(manager.getSubtask(7));
//        System.out.println(manager.getTask(2));
//        System.out.println(manager.getTask(2));
//        System.out.println(manager.getEpic(6));
//        System.out.println("-------------");
//        System.out.println(manager.getHistory());

    }
}
