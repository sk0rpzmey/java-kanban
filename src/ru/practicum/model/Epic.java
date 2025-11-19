package ru.practicum.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task {
    private final ArrayList<Integer> subtaskId;
    private LocalDateTime endTime;

    public Epic(String title, String description) {
        super(title, description, Status.NEW, 0, null);
        subtaskId = new ArrayList<>();
    }

    public Epic(String title, String description, int id) {
        super(title, description, id, Status.NEW, 0, null);
        subtaskId = new ArrayList<>();
    }

    public Epic(Epic anotherEpic) {
        super(anotherEpic);
        this.subtaskId = new ArrayList<>(anotherEpic.subtaskId);
        this.endTime = anotherEpic.endTime;
    }

    public void addSubtaskId(int id) {
        subtaskId.add(id);
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
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
    public TaskType getType() {
        return TaskType.EPIC;
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", subtaskId=" + subtaskId +
                ", duration=" + duration +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
