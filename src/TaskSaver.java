import java.util.Scanner;

public class TaskSaver {
    String userTask[] = new String[2];

    public void run() {
        saveUserTask();
        saveTask();
    }

    private Task saveTask() {
        if (userTask[0].equals(null)) {
            System.out.println("Поле Название должно быть заполнено");
            return null;
        } else {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Разделить задачу на подзадачи? Введите ");
            System.out.println("0 - Для сохранения задачи и выхода в меню: ");
            System.out.println("1 - Для разделения подзадачи: ");
            String answer = scanner.nextLine();
            if (answer.equals("0")) {
                hashCode(userTask[0], userTask[1]);
                Task task = new Task(userTask[0], userTask[1], hashCode(userTask[0], userTask[1]),
                        TaskStatus.NEW, false);
                return task;
            } else if (answer.equals("1")){
                saveUserTask();
                SubTask subTask = new SubTask();

                return null;
            } else {
                System.out.println("Введите верное значение (0 или 1)");
                return null;
            }
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
