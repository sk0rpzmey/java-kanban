import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subtaskId;

    public Epic(String title, String description, int id) {
        super(title, description, id, Status.NEW);
        subtaskId = new ArrayList<>();
    }

    public void addSubtaskId(int id) {
        subtaskId.add(id);
    }

    public void removeSubtaskId(int id) {
        subtaskId.remove((Integer) id);
    }

    public ArrayList<Integer> getSubtaskId() {
        return subtaskId;
    }

    public void updateStatus(ArrayList<Subtask> subtasks) {
        if (subtaskId.isEmpty()) {
            setStatus(Status.NEW);
            return;
        }

        boolean allDone = true;
        boolean allNew = true;

        for (Subtask subtask : subtasks) {
            if (subtask.getStatus() != Status.DONE) allDone = false;
            if (subtask.getStatus() != Status.NEW) allNew = false;
        }

        if (allDone) setStatus(Status.DONE);
        else if (allNew) setStatus(Status.NEW);
        else setStatus(Status.IN_PROGRESS);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id=" + getId() +
                ", title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", subtaskId=" + subtaskId +
                '}';
    }
}
