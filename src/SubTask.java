public class SubTask extends Task {

        String subTaskName;
        String subTaskDescription;
        int subTaskHash;
        TaskStatus subTaskStatus;

//    public SubTask(String subTaskName, String subTaskdescription, int hash, TaskStatus status) {
//        super();
//        this.subTaskName = subTaskName;
//        this.subTaskDescription = subTaskdescription;
//        this.subTaskHash = subTaskHash;
//        this.subTaskStatus = subTaskStatus;
//    }

    public int hashCode(String subTaskName, String subTaskDescription) {
        int hash = 19;
        if (name != null) {
            hash = name.hashCode();
        }
        if (description != null) {
            hash = hash + description.hashCode();
        }
        return hash;
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
