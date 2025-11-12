package ru.practicum.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    protected String title;
    protected String description;
    protected int id;
    protected Status status;
    protected Duration duration;
    protected LocalDateTime startTime;

    public Task(
            String title,
            String description,
            Status status,
            int durationInMinutes,
            LocalDateTime startTime
    ) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.duration = Duration.ofMinutes(durationInMinutes);
        this.startTime = startTime;
    }

    public Task(
            String title,
            String description,
            int id,
            Status status,
            int durationInMinutes,
            LocalDateTime startTime
    ) {
        this.title = title;
        this.description = description;
        this.id = id;
        this.status = status;
        this.duration = Duration.ofMinutes(durationInMinutes);
        this.startTime = startTime;
    }

    public Task(Task anotherTask) {
        this.title = anotherTask.title;
        this.description = anotherTask.description;
        this.id = anotherTask.id;
        this.status = anotherTask.status;
        this.duration = anotherTask.duration;
        this.startTime = anotherTask.startTime;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setDuration(Duration Minutes) {
        this.duration = Minutes;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public TaskType getType() {
        return TaskType.TASK;
    }

    public Duration getDuration() {
        return duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        if (startTime == null) {
            return null;
        }
        return startTime.plus(duration);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        Task otherTask = (Task) obj;
        return id == otherTask.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); // возвращаем итоговый хеш
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", duration=" + duration +
                ", startTime=" + startTime +
                '}';
    }
}
