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
        Task task = chooseUserTask();
        if (task != null) {
            return saveSubTask(task);
        } else {
            return null;
        }
    }

    public Task chooseUserTask() {
        for (Task task : TaskStorage.getTasks()) {
            System.out.println(task.toString());
        }
        Task task = null;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Выберите задачу по ID: ");
        int id = scanner.nextInt();
        for (Task taskChoose : TaskStorage.getTasks()) {
            if (taskChoose.getId() == id) {
                task = taskChoose;
            }
        }
        if (task == null) {
            System.out.println("Вы ввели неверный ID задачи");
        }
        return task;
    }
}
