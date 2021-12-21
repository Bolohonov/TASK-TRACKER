import java.util.LinkedList;
import java.util.Scanner;

public class TaskSaver {
    String userTask[] = new String[2];

    public Task saveTask() {
        saveUserTask();
        if (userTask[0].equals(null)) {
            System.out.println("Поле Название должно быть заполнено");
            return null;
        } else {
            int hash = hashCode(userTask[0], userTask[1]);
            Task task = new Task(userTask[0], userTask[1], hash,
                    TaskStatus.NEW, 0);
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите: ");
            System.out.println("0 - Для выхода в меню: ");
            System.out.println("1 - Для для добавления подзадач: ");
            String answer = scanner.nextLine();
            if (answer.equals("0")) {
                return task;
            } else if (answer.equals("1")){
                task.setEpic(1);
                SubTaskSaver subTaskSaver = new SubTaskSaver();
                subTaskSaver.saveSubTask(task);
                return task;
            } else {
                System.out.println("Вы ввели неверное значение!");
                return null;
            }
        }
    }

    private String[] saveUserTask() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите название задачи: ");
        userTask[0] = scanner.nextLine();
        System.out.println("Введите описание задачи (можно оставить поле пустым): ");
        userTask[1] = scanner.nextLine();
        return userTask;
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
}
