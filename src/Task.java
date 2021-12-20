public class Task {

    String name;
    String description;
    int hashCode;
    TaskStatus status;
    boolean epic;

//    public Task(String name, String description, int hash, TaskStatus status, boolean epic) {
//        this.name = name;
//        this.description = description;
//        this.hashCode = hash;
//        this.status = status;
//        this.epic = epic;
//    }

    public Task() {
    }

    public int hashCode(String name, String description) {
        int hash = 17;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getHash() {
        return hashCode;
    }

    public void setHash(int hash) {
        this.hashCode = hash;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public boolean isEpic() {
        return epic;
    }

    public void setEpic(boolean epic) {
        this.epic = epic;
    }
}
