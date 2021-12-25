package service;

public enum Menu {

    ONE("1 - Добавить новую задачу, эпик или подзадачу"),
    TWO("2 - Добавить новую подзадачу в эпик по идентификатору"),
    THREE("3 - Получить список всех подзадач"),
    FOUR("4 - Получить список всех эпиков"),
    FIVE("5 - Получить список всех подзадач определенного эпика"),
    SIX("6 - Получить задачи любого типа по идентификатору"),
    SEVEN("7 - Обновить задачу любого типа по идентификатору"),
    EIGHT("8 - Удалить все задачи"),
    NINE("9 - Удалить эпик и все подзадачи по идентификатору"),
    TEN("10 - Удалить подзадачу по идентификатору"),
    ZERO("0 - Выход из программы");


    private final String menuCommand;

    Menu(String menuCommand) {
        this.menuCommand = menuCommand;
    }

    public String getMenu() {
        return this.menuCommand;
    }

}
