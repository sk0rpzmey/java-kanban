package ru.practicum.model;

import java.util.ArrayList;

public class Epic extends Task {
    private final ArrayList<Integer> subtaskId;

    public Epic(String title, String description) {
        super(title, description, Status.NEW);
        subtaskId = new ArrayList<>();
    }

    public Epic(String title, String description, int id) {
        super(title, description, id, Status.NEW);
        subtaskId = new ArrayList<>();
    }

    public Epic(Epic anotherEpic) {
        super(anotherEpic);
        this.subtaskId = new ArrayList<>(anotherEpic.subtaskId);
    }

    public void addSubtaskId(int id) {
        subtaskId.add(id);
    }

    public void removeSubtaskId(int id) {
        subtaskId.remove((Integer) id);
    }

    public void removeSubtaskAllId() {
        subtaskId.clear();
    }

    public ArrayList<Integer> getSubtaskId() {
        return subtaskId;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", subtaskId=" + subtaskId +
                '}';
    }
}
