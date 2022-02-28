package main.java.repository;

import main.java.tasks.EpicTask;
import main.java.tasks.SingleTask;
import main.java.tasks.SubTask;
import main.java.tasks.Task;

public interface TaskCreator {

    SingleTask createSingleTask(String[] userTask);

    EpicTask createEpicTask(String[] userTask);

    SubTask createSubTask(Task epicTask, String[] userTask);
}
