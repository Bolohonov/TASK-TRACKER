package repository;

import tasks.EpicTask;
import tasks.SingleTask;
import tasks.SubTask;
import tasks.Task;

public interface Saveable {

    SingleTask createSingleTask(String[] userTask);

    EpicTask createEpicTask(String[] userTask);

    SubTask createSubTask(Task epicTask, String[] userTask);
}
