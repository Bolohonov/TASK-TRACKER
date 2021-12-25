package service;

import repository.EpicTaskRepository;
import repository.SubTaskRepository;
import repository.TaskStatus;
import tasks.EpicTask;
import tasks.SubTask;

public class SubTaskSaver {

    public static SubTask createSubTask(EpicTask epicTask) {
        SubTask subTask;
        String[] userTask = Scan.saveLinesFromUser();
        subTask = new SubTask(epicTask, userTask[0], userTask[1]);
        return subTask;
    }

    public static SubTask createSubTaskFromUserSelect() {
        EpicTask epicTask = EpicTaskRepository.selectUserTaskByID();
        if (epicTask != null) {
            return createSubTask(epicTask);
        } else {
            return null;
        }
    }

    public static EpicTask createSubTaskFromEpicTask(EpicTask epicTask) {
        int command = -1;
        while (command != 0) {
            Print.printMenuToAddSubTask();
            command = Scan.getIntOrZero();
            switch (command) {
                case 0:
                    break;
                case 1:
                    SubTaskRepository.setSubTaskStorage(epicTask);
                    epicTask.setStatus(TaskStatus.IN_PROGRESS);
                    break;
                default:
                    System.out.println("Вы ввели неверное значение!");
                    break;
            }
        }
        return epicTask;
    }
}
