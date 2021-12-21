import java.util.Scanner;

public class TaskToSave {

    public Task saveTask() {
        String name;
        String description;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите название задачи: ");
        name = scanner.nextLine();
        System.out.println("Введите описание задачи (можно оставить поле пустым): ");
        description = scanner.nextLine();
        System.out.println("Разделить задачу на подзадачи? Введите ");
        System.out.println("1 - Для разделения подзадачи: ");
        System.out.println("0 - Для сохранения задачи и выхода в меню: ");
        String answer = scanner.nextLine();
        if (answer.equals("1")) {

        } else if (answer.equals("0")){

        } else {
            System.out.println("Введите верное значение (1 или 0)");
        }
        if (name.equals(null)) {
            System.out.println("Поле Название должно быть заполнено");
            return null;
        } else {
            Task task = new Task();
            task.name = name;
            task.description = description;
            task.hashCode = task.hashCode(task.name, task.description);
            task.status = TaskStatus.NEW;
            task.epic = false;
            return task;
        }
    }


//    public SubTask saveSubTask() {
//        SubTask subTask = new SubTask();
//        System.out.println("Введите название задачи: ");
//        String answer = setAnswer();
//        if (!checkGoToPrevisionMenu(answer)) {
//            for (Task task : TaskStorage.tasks) {
//                if (answer.equals(task.name)) {
//                    System.out.println("Введите название подзадачи: ");
//                    answer = setAnswer();
//                    if (!checkGoToPrevisionMenu(answer)) {
//                        subTask.name = answer;
//                        System.out.println("Введите описание задачи: ");
//                        answer = setAnswer();
//                        if (!checkGoToPrevisionMenu(answer)) {
//                            subTask.description = answer;
//                            subTask.hashCode = subTask.hashCode(subTask.name, subTask.description);
//                            subTask.status = TaskStatus.NEW;
//                            task.epic = true;
//                        }
//                    }
//                }
//            }
//        }
//        if (subTask.name != null && subTask.description != null && subTask.hashCode != 0) {
//            return subTask;
//        } else return null;
//    }


    public boolean checkGoToPrevisionMenu(String zeroOrNot) {
        if (zeroOrNot.equals("0")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkNotNull(String nullOrNot) {
        if (nullOrNot.equals(null)) {
            return false;
        } else {
            return true;
        }
    }
}
