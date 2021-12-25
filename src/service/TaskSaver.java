package service;

import repository.TaskStatus;
import tasks.SingleTask;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TaskSaver {

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
