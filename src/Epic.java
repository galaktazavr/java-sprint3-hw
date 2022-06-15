import java.util.ArrayList;

public class Epic extends Task {
    ArrayList<Integer> subtaskId = new ArrayList<>();

    public Epic(String name, String description, int id, String status) {
        super(name, description, id, status);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subtaskId=" + subtaskId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status='" + status + '\'' +
                '}';
    }
}
