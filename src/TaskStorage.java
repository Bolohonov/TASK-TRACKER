import java.util.ArrayList;
import java.util.HashMap;

public class TaskStorage {

    private static TaskStorage taskStorage;
    HashMap<String, ArrayList<Task>> taskList = new HashMap<>();

    private TaskStorage () {
    }


    public HashMap<String, ArrayList<Task>> getTaskList() {
        return taskList;
    }

    public void setTaskList(HashMap<String, ArrayList<Task>> taskList) {
        this.taskList = taskList;
    }
}
