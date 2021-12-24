package service;

import repository.EpicTaskRepository;
import repository.SubTaskRepository;
import repository.TaskStatus;
import tasks.EpicTask;
import tasks.SubTask;

public class SubTaskSaver {

    EpicTask epicTask;

    public SubTask createTask(EpicTask epicTask) {
        String[] userTask = Scan.saveLinesFromUser();
        long id = 0;
        SubTask subTask = new SubTask(epicTask, userTask[0], userTask[1], id,
                TaskStatus.NEW);
        id = subTask.calcAndCheckId();
        subTask.setId(id);
        return subTask;
    }

    public static SubTask saveSubTaskFromUserSelect() {
        EpicTask epicTask = EpicTaskRepository.selectUserTaskByID();
        if (epicTask != null) {
            return createTask(epicTask);
        } else {
            return null;
        }
    }

    public static EpicTask saveSubTaskFromEpicTask(EpicTask epicTask) {
        int command = -1;
        while (command != 0) {
            Print.printMenuToAddSubTask();
            command = Scan.getScanIntCommandOrZero();
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
