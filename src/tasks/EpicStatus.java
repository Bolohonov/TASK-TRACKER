package tasks;

public enum EpicStatus {
    EPIC("Статус EPIC - имеются подзадачи"),
    NOT_EPIC("Статус NOT_EPIC - нет подзадач");

    private final String epicStatus;

    EpicStatus(String epicStatus) {
        this.epicStatus = epicStatus;
    }

    public String getEpicStatus() {
        return this.epicStatus;
    }
}
