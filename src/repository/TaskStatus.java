package repository;

public enum TaskStatus {
    NEW("Статус NEW присваивается при создании задачи"),
    IN_PROGRESS("Нажмите 2 - Статус IN_PROGRESS"),
    DONE("Нажмите 3 - Статус DONE");

    private final String taskStatus;

    TaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getStatus() {
        return this.taskStatus;
    }
}
