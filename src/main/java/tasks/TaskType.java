package tasks;

public enum TaskType {

    SINGLE("SINGLE"),
    EPIC("EPIC"),
    SUBTASK("SUBTASK");

    private final String taskType;

    TaskType(String taskType) {
        this.taskType = taskType;
    }
}
