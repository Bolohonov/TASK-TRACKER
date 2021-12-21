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
                    TaskStorage.setTaskStorage();
                    break;
                case 2:
                    SubTaskStorage.setSubTaskFromUserChoice();
                    break;
                case 3:
                    SubTaskStorage.getSubTasksList();
                    break;
                case 4:
                    SubTaskStorage.getSubTasksListFromUserChoice();
                    break;
                case 5:
                    SubTaskStorage.getSubTasksListFromUserChoice();
                    break;
                case 6: break;
                case 7: break;
                case 8: break;
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
