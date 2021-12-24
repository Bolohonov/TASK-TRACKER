package service;

import repository.TaskStatus;
import repository.EpicStatus;
import tasks.SingleTask;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TaskSaver extends TaskFactory{

    public static int selectType() {
        Print.printMenuToSaveTask();
        return Scan.getScanOrZero();
    }

    public static void saveFromCommand() {
        int command = selectType();
        if (command == 1) {
            TaskSaver.createTask();
        } else if (command == 2){
            EpicTaskSaver.saveUserTask();

        }
    }

    @Override
    public static SingleTask createTask() {
        SingleTask singleTask = null;
        String[] userTask = Scan.saveLinesFromUser();
        if (!userTask[0].equals(null)) {
            long id = 0;
            singleTask = new SingleTask(userTask[0], userTask[1], id,
                    TaskStatus.NEW);
            id = singleTask.calcAndCheckId();
            singleTask.setId(id);
        } else {
            System.out.println("Поле Название должно быть заполнено");
            return singleTask;
        }
        return singleTask;
    }


}
