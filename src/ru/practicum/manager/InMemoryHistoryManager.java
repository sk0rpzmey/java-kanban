package ru.practicum.manager;

import ru.practicum.model.*;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private Node<Task> head;
    private Node<Task> tail;
    private final Map<Integer, Node<Task>> uniqueBrowsingHistory = new HashMap<>();

    // Методы для работы с историей просмотров
    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }

        int taskId = task.getId();

        // Если задача уже есть в истории, удаляем её оттуда
        if (uniqueBrowsingHistory.containsKey(taskId)) {
            removeNode(uniqueBrowsingHistory.get(taskId));
        }

        // Добавляем задачу в конец списка
        linkLast(task);
    }

    @Override
    public void remove(int id) {
        if (uniqueBrowsingHistory.containsKey(id)) {
            removeNode(uniqueBrowsingHistory.get(id));
        }
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    // Добавляет задачу в конец списка
    private void linkLast(Task task) {
        Node<Task> newNode = new Node<>(tail, task, null);

        if (tail == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }

        tail = newNode;
        uniqueBrowsingHistory.put(task.getId(), newNode);
    }

    // Удаляет узел из списка
    private void removeNode(Node<Task> node) {
        if (node == null) {
            return;
        }

        // Обновляем ссылки соседних узлов
        if (node.prev == null) {
            head = node.next;
        } else {
            node.prev.next = node.next;
        }

        if (node.next == null) {
            tail = node.prev;
        } else {
            node.next.prev = node.prev;
        }

        // Удаляем из мапы
        uniqueBrowsingHistory.remove(node.item.getId());
    }

    // Собирает все задачи из списка в ArrayList
    private List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        Node<Task> current = head;

        while (current != null) {
            tasks.add(current.item);
            current = current.next;
        }

        return tasks;
    }
}
