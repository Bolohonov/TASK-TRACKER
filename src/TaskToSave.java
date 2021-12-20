import java.util.Scanner;

public class TaskToSave {

    public Task saveTask() {
        Task task = new Task();
        System.out.println("Введите название задачи: ");
        String answer = setAnswer();
        if (!checkGoToPrevisionMenu(answer)) {
            task.name = answer;
            System.out.println("Введите описание задачи: ");
            answer = setAnswer();
            if (!checkGoToPrevisionMenu(answer)) {
                task.description = answer;
                task.hashCode = task.hashCode();
                task.status = TaskStatus.NEW;
                task.epic = false;
            }
        }
        if (task.name != null && task.description != null && task.hashCode != 0) {
            return task;
        } else return null;
    }

    public String setAnswer() {
        System.out.println("0 - Для выхода в предыдущее меню: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public boolean checkGoToPrevisionMenu(String zeroOrNot) {
        if (zeroOrNot.equals("0")) {
            return true;
        } else {
            return false;
        }
    }
}
