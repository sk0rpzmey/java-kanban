package ru.practicum;
import ru.practicum.manager.*;
import ru.practicum.model.*;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();

        // Создаем две обычные задачи
        Task task1 = new Task("Помыть машину", "Полная мойка с полировкой", Status.NEW);
        Task task2 = new Task("Купить продукты", "Молоко, хлеб, яйца", Status.NEW);
        manager.createTask(task1);
        manager.createTask(task2);

        // Создаем первый эпик с двумя подзадачами
        Epic epic1 = new Epic("Ремонт квартиры", "Полный цикл работ");
        manager.createEpic(epic1);

        Subtask subtask1 = new Subtask("Демонтаж стен", "", epic1.getId(),  Status.NEW);
        Subtask subtask2 = new Subtask("Укладка плитки", "",  epic1.getId(), Status.NEW);
        manager.createSubtask(subtask1);
        manager.createSubtask(subtask2);

        // Создаем второй эпик с одной подзадачей
        Epic epic2 = new Epic("Подготовка к отпуску", "");
        manager.createEpic(epic2);

        Subtask subtask3 = new Subtask("Купить билеты", "", epic2.getId(), Status.NEW);
        manager.createSubtask(subtask3);

        // Распечатываем списки задач
        System.out.println("Список задач.");
        System.out.println(manager.getAllTasks());
        System.out.println("Список эпиков.");
        System.out.println(manager.getAllEpics());
        System.out.println("Список подзадач.");
        System.out.println(manager.getAllSubtasks());


        // Изменяем статусы
        task1.setStatus(Status.IN_PROGRESS);
        manager.updateTask(task1);

        subtask1.setStatus(Status.IN_PROGRESS);
        manager.updateSubtask(subtask1);

        subtask3.setStatus(Status.DONE);
        manager.updateSubtask(subtask3);

        System.out.println("Список эпиков после изменения статуса.");
        System.out.println(manager.getAllEpics());

        // Удаляем задачу и эпик
        manager.deleteTask(task1.getId());
        manager.deleteEpic(epic1.getId());

        // Распечатываем списки задач и эпиков
        System.out.println("Списки после удаления.");
        System.out.println("Список задач.");
        System.out.println(manager.getAllTasks());
        System.out.println("Список эпиков.");
        System.out.println(manager.getAllEpics());
        System.out.println("Список подзадач.");
        System.out.println(manager.getAllSubtasks());
    }
}
