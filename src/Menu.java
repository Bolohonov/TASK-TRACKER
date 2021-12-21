public enum Menu {

    ONE("1 - Добавить новую задачу"),
    TWO("2 - Добавить новую подзадачу"),
    THREE("3 - Получить список всех подзадач"),
    FOUR("4 - Получить список всех подзадач эпика"),
    FIVE("5 - Получить эпик и подзадачу по идентификатору"),
    SIX("6 - Обновить эпик по идентификатору"),
    SEVEN("7 - Обновить подзадачу по идентификатору"),
    EIGHT("8 - Удалить задачу"),
    ZERO("0 - Выход из программы");


    private final String menuCommand;

    Menu(String menuCommand) {
        this.menuCommand = menuCommand;
    }

    public String getMenu() {
        return this.menuCommand;
    }

}
