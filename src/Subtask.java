public class Subtask extends Task{
    private int epicId;

    public Subtask(String title, String description, int id, Status status) {
        super(title, description, id, status);
        this.epicId = id;
    }
    public void setEpicId (int epicId) { this.epicId = epicId; }
    public int getEpicId () { return epicId; }

    @Override
    public String toString() {
        return "Subtask{" +
                "id=" + getId() +
                ", title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", epicId=" + epicId +
                '}';
    }
}
