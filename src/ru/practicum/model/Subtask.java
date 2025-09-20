package ru.practicum.model;

public class Subtask extends Task {
    private int epicId;

    public Subtask(String title, String description, int id, Status status) {
        super(title, description, id, status);
        this.epicId = id;
    }

    public Subtask(Subtask anotherSubtask) {
        super(anotherSubtask);
        this.epicId = anotherSubtask.epicId;
    }

    public void setEpicId(int id) {
        epicId = id;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", epicId=" + epicId +
                '}';
    }
}
