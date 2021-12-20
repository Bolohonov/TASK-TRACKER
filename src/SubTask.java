public class SubTask extends Task {

        String subTaskName;
        String subTaskDescription;
        int subTaskHash;
        TaskStatus subTaskStatus;

    public SubTask(String subTaskName, String subTaskdescription, int hash, TaskStatus status) {
        super();
        this.subTaskName = subTaskName;
        this.subTaskDescription = subTaskdescription;
        this.subTaskHash = subTaskHash;
        this.subTaskStatus = subTaskStatus;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public String getSubTaskName() {
        return subTaskName;
    }

    public void setSubTaskName(String subTaskName) {
        this.subTaskName = subTaskName;
    }

    public String getSubTaskDescription() {
        return subTaskDescription;
    }

    public void setSubTaskDescription(String subTaskDescription) {
        this.subTaskDescription = subTaskDescription;
    }

    public int getSubTaskHash() {
        return subTaskHash;
    }

    public void setSubTaskHash(int subTaskHash) {
        this.subTaskHash = subTaskHash;
    }

    public TaskStatus getSubTaskStatus() {
        return subTaskStatus;
    }

    public void setSubTaskStatus(TaskStatus subTaskStatus) {
        this.subTaskStatus = subTaskStatus;
    }
}
