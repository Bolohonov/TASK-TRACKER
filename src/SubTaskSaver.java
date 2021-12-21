import java.util.LinkedList;
import java.util.Scanner;

public class SubTaskSaver extends TaskSaver{

    public SubTask saveSubTask(Task task) {
        saveUserTask();
        int hash = hashCode(userTask[0], userTask[1]);
        SubTask subTask = new SubTask(task, userTask[0], userTask[1], hash,
                TaskStatus.NEW);
        return subTask;
    }

    public SubTask saveSubTask() {
        chooseUserTask();
        int hash = hashCode(userTask[0], userTask[1]);
        SubTask subTask = new SubTask(task, userTask[0], userTask[1], hash,
                TaskStatus.NEW);
        return subTask;
    }


    private String[] saveUserTask() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите название задачи: ");
        userTask[0] = scanner.nextLine();
        System.out.println("Введите описание задачи (можно оставить поле пустым): ");
        userTask[1] = scanner.nextLine();
        return userTask;
    }

    private Task chooseUserTask() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Выберите название задачи по ID: ");
        for (Task task : TaskStorage.getTaskStorage().getTasks()) {
            System.out.println(task.toString());
        }
        userTask[0] = scanner.nextLine();

        return userTask;
    }

}
