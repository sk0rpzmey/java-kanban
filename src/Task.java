import java.util.Objects;

public class Task {
    private String title;
    private String description;
    private int id;
    Status status;

    public Task(String title, String description, int id, Status status) {
        this.title = title;
        this.description = description;
        this.id = id;
        this.status = status;
    }

    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setId(int id) { this.id = id; }
    public void setStatus(Status status) { this.status = status; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public int getId() { return id; }
    public Status getStatus() { return status; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        Task otherTask = (Task) obj;
        return Objects.equals(title, otherTask.title) &&
                Objects.equals(description, otherTask.description) &&
                (id == otherTask.id) && (status.equals(otherTask.status));
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
                '}';
    }
}
