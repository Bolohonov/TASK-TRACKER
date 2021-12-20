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
                task.hashCode = task.hashCode(task.name, task.description);
                task.status = TaskStatus.NEW;
                task.epic = false;
            }
        }
        if (task.name != null && task.description != null && task.hashCode != 0) {
            return task;
        } else return null;
    }

    public SubTask saveSubTask() {
        SubTask subTask = new SubTask();
        System.out.println("Введите название задачи: ");
        String answer = setAnswer();
        if (!checkGoToPrevisionMenu(answer)) {
            for (Task task : TaskStorage.tasks) {
                if (answer.equals(task.name)) {
                    System.out.println("Введите название подзадачи: ");
                    answer = setAnswer();
                    if (!checkGoToPrevisionMenu(answer)) {
                        subTask.name = answer;
                        System.out.println("Введите описание задачи: ");
                        answer = setAnswer();
                        if (!checkGoToPrevisionMenu(answer)) {
                            subTask.description = answer;
                            subTask.hashCode = subTask.hashCode(subTask.name, subTask.description);
                            subTask.status = TaskStatus.NEW;
                            task.epic = true;
                        }
                    }
                }
            }
        }
        if (subTask.name != null && subTask.description != null && subTask.hashCode != 0) {
            return subTask;
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
