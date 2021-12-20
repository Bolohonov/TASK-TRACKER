import java.util.Scanner;

public class CommandManager {

    Scanner scanner = new Scanner(System.in);

    public void getCommamndPanel() {

        while (true) {
            printMenu();

            int command = scanner.nextInt();

            switch (command) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:

                default:
                    System.out.println("Извините, такой команды пока нет.");
                    break;
            }
        }
    }

    private void printMenu() {
        System.out.println("Выберите действие: ");
        System.out.println("1 - Считать все месячные отчёты");
        System.out.println("2 - Считать годовой отчёт");
        System.out.println("3 - Сверить отчёты");
        System.out.println("4 - Вывести информацию о всех месячных отчётах");
        System.out.println("5 - Вывести информацию о годовом отчёте");
        System.out.println("0 - Выход из программы");
    }

}
