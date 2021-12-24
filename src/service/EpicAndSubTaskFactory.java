package service;

import repository.EpicTaskRepository;
import repository.SubTaskRepository;
import repository.TaskStatus;
import tasks.EpicTask;
import tasks.SubTask;
import tasks.Task;

public class EpicAndSubTaskFactory extends TaskFactory{

    private static Task task;
    @Override
    public Task createTask() {
        if (!Scan.getLinesFromUser()[0].equals(null)) {
            task = new EpicTask(Scan.getLinesFromUser()[0], Scan.getLinesFromUser()[1], 0,
                    TaskStatus.NEW);
            task.setId(task.calcAndCheckId());
        } else {
            System.out.println("Поле Название должно быть заполнено");
            return task;
        }
        return task;
    }

    public Task createSubTask(EpicTask epicTask) {
        Scan.getLinesFromUser();
        task = new SubTask(epicTask, Scan.getLinesFromUser()[0], Scan.getLinesFromUser()[1], 0,
                TaskStatus.NEW);
        task.setId(task.calcAndCheckId());
        return task;
    }

    public Task createSubTaskFromUserSelect() {
        task = EpicTaskRepository.selectUserTaskByID();
        if (task != null) {
            return createSubTask((EpicTask) task);
        } else {
            return null;
        }
    }

    public static Task createSubTaskFromEpicTask(EpicTask epicTask) {
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
