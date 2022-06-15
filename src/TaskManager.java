import java.util.HashMap;
import java.util.Map;

public class TaskManager {
    Map<Integer, Task> taskList = new HashMap<>();
    Map<Integer, Subtask> subtaskList = new HashMap<>();
    Map<Integer, Epic> epicList = new HashMap<>();

    int id = 1; //значение в этом поле должно каким то образом каждый раз увеличиваться на 1
}
