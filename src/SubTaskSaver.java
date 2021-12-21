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

    public SubTask saveSubTaskFromUserChoice() {
        if (chooseUserTask() != null) {
            return saveSubTask(chooseUserTask());
        } else {
            return null;
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

    private Task chooseUserTask() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Выберите задачу по ID: ");
        for (Task task : TaskStorage.getTaskStorage().getTasks()) {
            System.out.println(task.toString());
        }
        int id = scanner.nextInt();

        Task task = null;
        for (Task taskChoose : TaskStorage.getTaskStorage().getTasks()) {
            if (taskChoose.getId() == id) {
                task = taskChoose;
            } else {
                System.out.println("Вы ввели неверный ID задачи");
            }
        }
        return task;
    }

}
