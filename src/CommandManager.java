import java.util.Scanner;

public class CommandManager {

    Scanner scanner = new Scanner(System.in);

    public void getCommandPanel() {
        int command = -1;

        while (command != 0) {
            printMenu();
            command = scanner.nextInt();

            switch (command) {
                case 1:
                    TaskStorage.getTaskStorage().setTaskStorage();
                    break;
                case 2:
                    SubTaskStorage.getSubTaskStorage().setSubTaskFromUserChoice();
                case 3:
                    SubTaskStorage.getSubTaskStorage().getSubTasksList();
                case 4:
                    SubTaskStorage.getSubTaskStorage().getSubTasksListFromUserChoice();
                case 5:
                case 6:
                case 7:
                case 8:
                case 0:
                    System.out.println("Работа программы завершена!");
                    break;
                default:
                    System.out.println("Извините, такой команды пока нет.");
                    break;
            }
        }
    }

    private void printMenu() {
        System.out.println("Выберите действие: ");
        for (Menu m : Menu.values()) {
            System.out.println(m.getMenu());
        }
    }

}
