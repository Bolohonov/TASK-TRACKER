public class Task {

    String name;
    String description;
    int hash;
    TaskStatus status;
    boolean epic;

    public Task(String name, String description, int hash, TaskStatus status, boolean epic) {
        this.name = name;
        this.description = description;
        this.hash = hash;
        this.status = status;
        this.epic = epic;
    }

    public Task() {
    }

    @Override
    public int hashCode() {
        return super.hashCode();
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
        return hash;
    }

    public void setHash(int hash) {
        this.hash = hash;
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
